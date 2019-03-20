package al.bruno.personal.expense

import al.bruno.personal.expense.adapter.CustomAdapter
import al.bruno.personal.expense.callback.BindingData
import al.bruno.personal.expense.dependency.injection.InjectionProvider.provideExpenseChartInjection
import al.bruno.personal.expense.callback.OnClick
import al.bruno.personal.expense.data.source.local.AppDatabase.Companion.getInstance
import al.bruno.personal.expense.databinding.ExpenseMasterSingleItemBinding
import al.bruno.personal.expense.databinding.FragmentHomeBinding
import al.bruno.personal.expense.dependency.injection.InjectionProvider.provideExpenseDetailsInjection
import al.bruno.personal.expense.dependency.injection.InjectionProvider.provideExpenseMasterInjection
import al.bruno.personal.expense.entities.Chart
import al.bruno.personal.expense.entities.ChartDataObject
import al.bruno.personal.expense.entities.ExpenseChart
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import al.bruno.personal.expense.observer.Observer
import al.bruno.personal.expense.entities.Month
import al.bruno.personal.expense.model.ExpenseDetails
import al.bruno.personal.expense.model.ExpenseMaster
import al.bruno.personal.expense.util.Utilities
import al.bruno.personal.expense.util.Utilities.month
import al.bruno.personal.expense.util.ViewModelProviderFactory
import al.bruno.personal.expense.view.model.ExpenseChartViewModel
import al.bruno.personal.expense.view.model.ExpenseDetailsViewModel
import al.bruno.personal.expense.view.model.ExpenseMasterViewModel
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.github.mikephil.charting.utils.EntryXComparator
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.*

class HomeFragment : Fragment(), Observer<Month> {
    private val disposable : CompositeDisposable = CompositeDisposable()
    private var fragmentHomeBinding: FragmentHomeBinding? = null
    private val calendar = Calendar.getInstance()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        fragmentHomeBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        disposable.addAll(
                ViewModelProviders
                        .of(this, ViewModelProviderFactory(ExpenseChartViewModel(provideExpenseChartInjection(getInstance(context!!)))))[ExpenseChartViewModel::class.java]
                        .chart(month(calendar.get(Calendar.MONTH)), calendar.get(Calendar.YEAR).toString())
                        .subscribeOn(Schedulers.io())
                        .subscribe({
                            fragmentHomeBinding!!.chartData = chart(it)
                        },{
                            Log.i(HomeFragment::class.java.name, it.message)
                        }),

                ViewModelProviders
                        .of(this, ViewModelProviderFactory(ExpenseDetailsViewModel(provideExpenseDetailsInjection(getInstance(context!!)))))[ExpenseDetailsViewModel::class.java]
                        .budgetDetails(month(calendar.get(Calendar.MONTH)), calendar.get(Calendar.YEAR).toString())
                        .subscribeOn(Schedulers.io())
                        .subscribe({
                            fragmentHomeBinding?.expenseDetails = it
                        },{
                            Log.i(HomeFragment::class.java.name, it.message)
                        }),

                ViewModelProviders
                        .of(this, ViewModelProviderFactory(ExpenseMasterViewModel(provideExpenseMasterInjection(getInstance(context!!)))))[ExpenseMasterViewModel::class.java]
                        .expenseMaster(month(calendar.get(Calendar.MONTH)), calendar[Calendar.YEAR].toString())
                        .subscribeOn(Schedulers.io())
                        .subscribe({
                            fragmentHomeBinding!!.logAdapter = CustomAdapter(it, R.layout.expense_master_single_item, object : BindingData<ExpenseMaster, ExpenseMasterSingleItemBinding> {
                                override fun bindData(t: ExpenseMaster, vm: ExpenseMasterSingleItemBinding) {
                                    vm.master = t
                                }
                            })
                            Log.i(ExpenseMasterViewModel::class.java.name, it.toString())
                        },{
                            Log.i(ExpenseMasterViewModel::class.java.name, it.message)
                        })
        )
        fragmentHomeBinding?.incomesOnClick = object : OnClick {
            override fun onClick() {
                //TODO
            }
        }
        fragmentHomeBinding?.expenseOnClick = object : OnClick {
            override fun onClick() {
                fragmentManager!!
                        .beginTransaction()
                        .replace(R.id.host, DetailsFragment())
                        .addToBackStack("DETAILS_FRAGMENT")
                        .commit()
            }
        }

        fragmentHomeBinding?.addExpenseOnClick = object : OnClick {
            override fun onClick() {
                fragmentManager!!
                        .beginTransaction()
                        .replace(R.id.host, PersonalExpensesFragment())
                        .addToBackStack("EXPENSE_CATEGORIES_FRAGMENT")
                        .commit()
            }
        }

        return fragmentHomeBinding?.root
    }

    override fun update(t: Month) {
        disposable.addAll(
                ViewModelProviders
                        .of(this, ViewModelProviderFactory(ExpenseChartViewModel(provideExpenseChartInjection(getInstance(context!!)))))[ExpenseChartViewModel::class.java]
                        .chart(month(calendar.get(Calendar.MONTH)), calendar.get(Calendar.YEAR).toString())
                        .subscribeOn(Schedulers.io())
                        .subscribe({
                            fragmentHomeBinding?.chartData = chart(it)
                        },{
                            Log.i(HomeFragment::class.java.name, it.message)
                        }),

                ViewModelProviders
                        .of(this, ViewModelProviderFactory(ExpenseDetailsViewModel(provideExpenseDetailsInjection(getInstance(context!!)))))[ExpenseDetailsViewModel::class.java]
                        .budgetDetails(month(t.calendar().get(Calendar.MONTH)), t.calendar()[Calendar.YEAR].toString())
                        .subscribeOn(Schedulers.io())
                        .subscribe({
                            fragmentHomeBinding?.expenseDetails = it
                        },{
                            fragmentHomeBinding?.expenseDetails = ExpenseDetails()
                            Log.i(HomeFragment::class.java.name, it.message)
                        }),

                ViewModelProviders
                        .of(this, ViewModelProviderFactory(ExpenseMasterViewModel(provideExpenseMasterInjection(getInstance(context!!)))))[ExpenseMasterViewModel::class.java]
                        .expenseMaster(month(t.calendar().get(Calendar.MONTH)), t.calendar()[Calendar.YEAR].toString())
                        .subscribeOn(Schedulers.io())
                        .subscribe({
                            fragmentHomeBinding?.logAdapter = CustomAdapter(it, R.layout.expense_master_single_item, object : BindingData<ExpenseMaster, ExpenseMasterSingleItemBinding> {
                                override fun bindData(t: ExpenseMaster, vm: ExpenseMasterSingleItemBinding) {
                                    vm.master = t
                                }
                            })
                            Log.i(ExpenseMasterViewModel::class.java.name, it.toString())
                        },{
                            Log.i(ExpenseMasterViewModel::class.java.name, it.message)
                        }))
    }

    override fun onStop() {
        super.onStop()
        disposable.clear()
    }
    private fun setLineData(expenseCharts: List<ExpenseChart>): ChartDataObject<MutableList<String>, LineData>? {
        val dataSets = ArrayList<ILineDataSet>()
        val dateXaxis = ArrayList<String>()
        for (expenseChart in expenseCharts) {
            val entryList = ArrayList<Entry>()
            for (i in 0 until expenseChart.expenses!!.size) {
                val expense = expenseChart.expenses!!.get(i)
                entryList.add(Entry(i.toFloat(), expense.amount.toFloat()))
                dateXaxis.add(Utilities.date(expense.date!!))
            }
            //Sort
            Collections.sort(entryList, EntryXComparator())
            val lineDataSet = LineDataSet(entryList, expenseChart.type)
            lineDataSet.color = Utilities.randomColors()!!
            lineDataSet.lineWidth = 2f
            lineDataSet.circleRadius = 2f
            dataSets.add(lineDataSet)
        }
        return ChartDataObject(dateXaxis, LineData(dataSets))
    }
    private fun chart(charts: List<Chart>): ChartDataObject<MutableList<String>, LineData>? {
        val dataSets = ArrayList<ILineDataSet>()
        val dateXaxis = ArrayList<String>()
        for (chart in charts) {
            val entryList = ArrayList<Entry>()
            for (i in 0 until chart.expenses!!.size) {
                val expense = chart.expenses!!.get(i)
                entryList.add(Entry(i.toFloat(), expense.amount))
                dateXaxis.add(Utilities.date(expense.date!!))
            }
            //Sort
            Collections.sort(entryList, EntryXComparator())
            val lineDataSet = LineDataSet(entryList, chart.type)
            lineDataSet.color = Utilities.randomColors()!!
            lineDataSet.lineWidth = 2f
            lineDataSet.circleRadius = 2f
            dataSets.add(lineDataSet)
        }
        return ChartDataObject(dateXaxis, LineData(dataSets))
    }
}
