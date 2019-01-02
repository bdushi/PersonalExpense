package al.bruno.personal.expense

import al.bruno.personal.expense.adapter.CustomAdapter
import al.bruno.personal.expense.callback.BindingData
import al.bruno.personal.expense.dependency.injection.InjectionProvider.provideExpenseDetailsInjection
import al.bruno.personal.expense.callback.OnClick
import al.bruno.personal.expense.databinding.FragmentHomeBinding
import al.bruno.personal.expense.databinding.LogSingleItemBinding
import al.bruno.personal.expense.entities.ChartDataObject
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.IValueFormatter
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.utils.ColorTemplate
import com.github.mikephil.charting.utils.MPPointF

import java.text.DecimalFormat

import al.bruno.personal.expense.model.ExpenseDetails
import al.bruno.personal.expense.model.Expense
import al.bruno.personal.expense.util.Utilities.month
import al.bruno.personal.expense.util.Utilities.monthFormat
import al.bruno.personal.expense.util.ViewModelProviderFactory
import al.bruno.personal.expense.view.model.ExpenseDetailsViewModel
import al.bruno.personal.expense.view.model.ExpenseViewModel
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.*

class HomeFragment : Fragment() {
    private var calendar = Calendar.getInstance()
    private val disposable : CompositeDisposable = CompositeDisposable()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val fragmentHomeBinding: FragmentHomeBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        val factory = ViewModelProviderFactory(ExpenseDetailsViewModel(provideExpenseDetailsInjection(context!!)))
        disposable.addAll(
                ViewModelProviders
                        .of(this, factory)[ExpenseDetailsViewModel::class.java]
                        .budgetDetails(month(calendar.get(Calendar.MONTH)), calendar.get(Calendar.YEAR).toString())
                        .subscribeOn(Schedulers.io())
                        .subscribe({
                            fragmentHomeBinding.expenseDetails = it
                            fragmentHomeBinding.pieData = setData(budgetDetails = it)
                        },{
                            fragmentHomeBinding.expenseDetails = null
                            Log.i(HomeFragment::class.java.name, it.message)
                        }),
                ViewModelProviders.of(this@HomeFragment)[ExpenseViewModel::class.java]
                        .expenses(month(calendar.get(Calendar.MONTH)), calendar[Calendar.YEAR].toString())
                        .subscribeOn(Schedulers.io())
                        .subscribe({
                            fragmentHomeBinding.logAdapter = CustomAdapter(it, R.layout.log_single_item, object : BindingData<Expense, LogSingleItemBinding> {
                                override fun bindData(t: Expense, vm: LogSingleItemBinding) {
                                    vm.setExpense(t)
                                }
                            })
                        }, {
                            Log.i(HomeFragment::class.java.name, it.message)
                        }))

        fragmentHomeBinding.date.text = monthFormat(calendar.timeInMillis)

        fragmentHomeBinding.decrementOnClick = object : OnClick {
            override fun onClick() {
                calendar.add(Calendar.MONTH, -1)
                fragmentHomeBinding.date.text = monthFormat(calendar.timeInMillis)
                disposable.addAll(
                        ViewModelProviders
                                .of(this@HomeFragment, factory)[ExpenseDetailsViewModel::class.java]
                                .budgetDetails(month(calendar.get(Calendar.MONTH)), calendar.get(Calendar.YEAR).toString())
                                .subscribeOn(Schedulers.io())
                                .subscribe({
                                    fragmentHomeBinding.expenseDetails = it
                                    fragmentHomeBinding.pieData = setData(budgetDetails = it)
                                }, {
                                    fragmentHomeBinding.expenseDetails = null
                                    Log.i(HomeFragment::class.java.name, it.message)
                                }),
                        ViewModelProviders.of(this@HomeFragment)[ExpenseViewModel::class.java]
                                .expenses(month(calendar.get(Calendar.MONTH)), calendar[Calendar.YEAR].toString())
                                .subscribeOn(Schedulers.io())
                                .subscribe({
                                    fragmentHomeBinding.logAdapter = CustomAdapter(it, R.layout.log_single_item, object : BindingData<Expense, LogSingleItemBinding> {
                                        override fun bindData(t: Expense, vm: LogSingleItemBinding) {
                                            vm.setExpense(t)
                                        }
                                    })
                                }, {
                                    Log.i(HomeFragment::class.java.name, it.message)
                                }))
            }
        }
        fragmentHomeBinding.incrementOnClick = object : OnClick {
            override fun onClick() {
                calendar.add(Calendar.MONTH, 1)
                fragmentHomeBinding.date.text = monthFormat(calendar.timeInMillis)
                disposable.addAll(
                        ViewModelProviders
                                .of(this@HomeFragment, factory)[ExpenseDetailsViewModel::class.java]
                                .budgetDetails(month(calendar.get(Calendar.MONTH)), calendar.get(Calendar.YEAR).toString())
                                .subscribeOn(Schedulers.io())
                                .subscribe({
                                    fragmentHomeBinding.expenseDetails = it
                                    fragmentHomeBinding.pieData = setData(budgetDetails = it)
                                }, {
                                    fragmentHomeBinding.expenseDetails = null
                                    Log.i(HomeFragment::class.java.name, it.message)
                                }),
                        ViewModelProviders.of(this@HomeFragment)[ExpenseViewModel::class.java]
                                .expenses(month(calendar.get(Calendar.MONTH)), calendar[Calendar.YEAR].toString())
                                .subscribeOn(Schedulers.io())
                                .subscribe({
                                    fragmentHomeBinding.logAdapter = CustomAdapter(it, R.layout.log_single_item, object : BindingData<Expense, LogSingleItemBinding> {
                                        override fun bindData(t: Expense, vm: LogSingleItemBinding) {
                                            vm.setExpense(t)
                                        }
                                    })
                                }, {
                                    Log.i(HomeFragment::class.java.name, it.message)
                                }))
            }
        }

        fragmentHomeBinding.incomesOnClick = object : OnClick {
            override fun onClick() {
                //TODO
                /*fragmentManager!!
                        .beginTransaction()
                        .replace(R.id.host, ExpenseFragment())
                        .addToBackStack("EXPENSE_FRAGMENT")
                        .commit()*/
            }
        }
        fragmentHomeBinding.expenseOnClick = object : OnClick {
            override fun onClick() {
                fragmentManager!!
                        .beginTransaction()
                        .replace(R.id.host, DetailsFragment())
                        .addToBackStack("DETAILS_FRAGMENT")
                        .commit()
            }
        }

        fragmentHomeBinding.addExpenseOnClick = object : OnClick {
            override fun onClick() {
                fragmentManager!!
                        .beginTransaction()
                        .replace(R.id.host, PersonalExpensesFragment())
                        .addToBackStack("EXPENSE_CATEGORIES_FRAGMENT")
                        .commit()
            }
        }

        return fragmentHomeBinding.root
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

        val colors = ArrayList<Int>()

        for (c in ColorTemplate.VORDIPLOM_COLORS) {
            colors.add(c)
        }
        for (c in ColorTemplate.JOYFUL_COLORS) {
            colors.add(c)
        }
        for (c in ColorTemplate.COLORFUL_COLORS) {
            colors.add(c)
        }
        for (c in ColorTemplate.LIBERTY_COLORS) {
            colors.add(c)
        }
        for (c in ColorTemplate.PASTEL_COLORS) {
            colors.add(c)
        }

        colors.add(ColorTemplate.getHoloBlue())

        dataSet.colors = colors
        dataSet.selectionShift = 0f

        val data = PieData(dataSet)
        data.setValueFormatter(PercentFormatter())
        data.setValueTextSize(11f)
        data.setValueTextColor(Color.WHITE)
        return ChartDataObject(getString(R.string.app_name), pieData = data)
    }
}
