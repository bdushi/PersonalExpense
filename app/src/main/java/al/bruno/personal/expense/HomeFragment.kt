package al.bruno.personal.expense

import al.bruno.personal.expense.adapter.CustomAdapter
import al.bruno.personal.expense.callback.BindingData
import al.bruno.personal.expense.dependency.injection.InjectionProvider.provideExpenseDetailsInjection
import al.bruno.personal.expense.dependency.injection.InjectionProvider.provideExpenseChartInjection
import al.bruno.personal.expense.callback.OnClick
import al.bruno.personal.expense.databinding.ExpenseMasterSingleItemBinding
import al.bruno.personal.expense.databinding.FragmentHomeBinding
import al.bruno.personal.expense.dependency.injection.InjectionProvider.provideExpenseMasterInjection
import al.bruno.personal.expense.entities.ChartDataObject
import al.bruno.personal.expense.entities.ExpenseChart
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.github.mikephil.charting.formatter.IValueFormatter
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.utils.MPPointF

import java.text.DecimalFormat

import al.bruno.personal.expense.model.ExpenseDetails
import al.bruno.personal.expense.observer.Observer
import al.bruno.personal.expense.entities.Month
import al.bruno.personal.expense.model.Expense
import al.bruno.personal.expense.model.ExpenseMaster
import al.bruno.personal.expense.util.Utilities
import al.bruno.personal.expense.util.Utilities.colors
import al.bruno.personal.expense.util.Utilities.month
import al.bruno.personal.expense.util.ViewModelProviderFactory
import al.bruno.personal.expense.view.model.ExpenseChartViewModel
import al.bruno.personal.expense.view.model.ExpenseDetailsViewModel
import al.bruno.personal.expense.view.model.ExpenseMasterViewModel
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.github.mikephil.charting.utils.EntryXComparator
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.*

import com.github.mikephil.charting.components.Legend.LegendHorizontalAlignment.CENTER

class HomeFragment : Fragment(), Observer<Month> {
    private val disposable : CompositeDisposable = CompositeDisposable()
    private var fragmentHomeBinding: FragmentHomeBinding? = null
    private val calendar = Calendar.getInstance()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        fragmentHomeBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        disposable.addAll(
                ViewModelProviders
                        .of(this, ViewModelProviderFactory(ExpenseChartViewModel(provideExpenseChartInjection(context!!))))[ExpenseChartViewModel::class.java]
                        .expenseChart(month(calendar.get(Calendar.MONTH)), calendar.get(Calendar.YEAR).toString())
                        .subscribeOn(Schedulers.io())
                        .subscribe({
                            setLineChart(fragmentHomeBinding!!.lineChart, setLineData(it))
                        },{
                            Log.i(HomeFragment::class.java.name, it.message)
                        }),

                ViewModelProviders
                        .of(this, ViewModelProviderFactory(ExpenseDetailsViewModel(provideExpenseDetailsInjection(context!!))))[ExpenseDetailsViewModel::class.java]
                        .budgetDetails(month(calendar.get(Calendar.MONTH)), calendar.get(Calendar.YEAR).toString())
                        .subscribeOn(Schedulers.io())
                        .subscribe({
                            fragmentHomeBinding?.expenseDetails = it
                            //fragmentHomeBinding?.pieData = setData(budgetDetails = it)
                        },{
                            fragmentHomeBinding?.expenseDetails = ExpenseDetails()
                            Log.i(HomeFragment::class.java.name, it.message)
                        }),

                ViewModelProviders
                        .of(this, ViewModelProviderFactory(ExpenseMasterViewModel(provideExpenseMasterInjection(context!!))))[ExpenseMasterViewModel::class.java]
                        .expenseMaster(month(calendar.get(Calendar.MONTH)), calendar[Calendar.YEAR].toString())
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
                        })
        )
        fragmentHomeBinding?.incomesOnClick = object : OnClick {
            override fun onClick() {
                //TODO
                /*fragmentManager!!
                        .beginTransaction()
                        .replace(R.label.host, ExpenseFragment())
                        .addToBackStack("EXPENSE_FRAGMENT")
                        .commit()*/
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
                        .of(this, ViewModelProviderFactory(ExpenseChartViewModel(provideExpenseChartInjection(context!!))))[ExpenseChartViewModel::class.java]
                        .expenseChart(month(calendar.get(Calendar.MONTH)), calendar.get(Calendar.YEAR).toString())
                        .subscribeOn(Schedulers.io())
                        .subscribe({
                            setLineChart(fragmentHomeBinding!!.lineChart, setLineData(it))
                        },{
                            Log.i(HomeFragment::class.java.name, it.message)
                        }),

                ViewModelProviders
                        .of(this@HomeFragment, ViewModelProviderFactory(ExpenseDetailsViewModel(provideExpenseDetailsInjection(context!!))))[ExpenseDetailsViewModel::class.java]
                        .budgetDetails(month(t.calendar().get(Calendar.MONTH)), t.calendar().get(Calendar.YEAR).toString())
                        .subscribeOn(Schedulers.io())
                        .subscribe({
                            fragmentHomeBinding?.expenseDetails = it
                            //fragmentHomeBinding?.pieData = setData(budgetDetails = it)
                        }, {
                            fragmentHomeBinding?.expenseDetails = ExpenseDetails()
                            Log.i(HomeFragment::class.java.name, it.message)
                        }),

                ViewModelProviders
                        .of(this, ViewModelProviderFactory(ExpenseMasterViewModel(provideExpenseMasterInjection(context!!))))[ExpenseMasterViewModel::class.java]
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

    private fun setData(budgetDetails: ExpenseDetails): ChartDataObject<String, PieData> {
        val entries = ArrayList<PieEntry>()
        entries.add(PieEntry((budgetDetails.expense / budgetDetails.incomes) * 100, getString(R.string.expense)))
        entries.add(PieEntry((budgetDetails.balance / budgetDetails.incomes) * 100, getString(R.string.balance)))
        val dataSet = PieDataSet(entries, "")

        val format = DecimalFormat("### %")
        dataSet.setDrawIcons(false)
        dataSet.sliceSpace = 3f
        dataSet.iconsOffset = MPPointF(0f, 40f)
        dataSet.selectionShift = 5f
        dataSet.valueFormatter = IValueFormatter { value, entry, dataSetIndex, viewPortHandler -> format.format(value.toDouble()) }

        dataSet.colors = colors()
        dataSet.selectionShift = 0f

        val data = PieData(dataSet)
        data.setValueFormatter(PercentFormatter())
        data.setValueTextSize(11f)
        data.setValueTextColor(Color.WHITE)
        return ChartDataObject(getString(R.string.app_name), data = data)
    }

    private fun setLineData(expenseCharts: List<ExpenseChart>): LineData {
        val dataSets = ArrayList<ILineDataSet>()
        for (expenseChart in expenseCharts) {
            val entryList = ArrayList<Entry>()
            for (i in 0 until expenseChart.expenses!!.size) {
                val expense = expenseChart.expenses!!.get(i)
                entryList.add(Entry(i.toFloat(), expense.amount.toFloat()))
            }
            //Sort
            Collections.sort(entryList, EntryXComparator())
            val lineDataSet = LineDataSet(entryList, expenseChart.type)
            lineDataSet.color = Utilities.randomColors()!!
            lineDataSet.lineWidth = 2f
            lineDataSet.circleRadius = 3f
            dataSets.add(lineDataSet)
        }

        return LineData(dataSets)
    }

    fun setLineChart(lineChart: LineChart, lineData:LineData) {
        //
        val xAxis = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        lineChart.isHorizontalScrollBarEnabled = true
        lineChart.setPinchZoom(true)
        lineChart.getAxis(YAxis.AxisDependency.LEFT).axisLineColor = Color.BLUE
        lineChart.axisRight.isEnabled = false // no right axis
        //lineChart.setNoDataText(getString(R.string.no_data));
        lineChart.visibility = View.VISIBLE
        //Enable legend - Nuk mund te vendoset pershkrim ne grafik por
        lineChart.getLegend().setEnabled(true);
        lineChart.xAxis.position = XAxis.XAxisPosition.BOTTOM
        lineChart.xAxis.setAvoidFirstLastClipping(false)
        // Line chart Description
        lineChart.getDescription().setText(getString(R.string.expenses));
        //
        lineChart.xAxis.setValueFormatter { value, axis -> Utilities.days(value.toInt()) }
        lineChart.setMaxVisibleValueCount(xAxis)

        lineChart.data = lineData
        lineChart.invalidate()
    }
}
