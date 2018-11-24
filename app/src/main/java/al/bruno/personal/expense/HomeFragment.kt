package al.bruno.personal.expense

import al.bruno.personal.expense.callback.OnClick
import al.bruno.personal.expense.databinding.FragmentHomeBinding
import al.bruno.personal.expense.dependency.injection.BudgetDetailsInjection.provideBudgetDetailsInjection
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
import java.util.ArrayList
import java.util.Calendar

import al.bruno.personal.expense.model.BudgetDetails
import al.bruno.personal.expense.util.Utilities.month
import al.bruno.personal.expense.util.Utilities.monthFormat
import al.bruno.personal.expense.util.Utilities.monthIncrementAndDecrement
import al.bruno.personal.expense.util.ViewModelProviderFactory
import al.bruno.personal.expense.view.model.BudgetDetailsViewModel
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class HomeFragment : Fragment() {
    private var calendar = Calendar.getInstance()
    private val disposable : CompositeDisposable = CompositeDisposable()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val fragmentManager: FragmentHomeBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        val factory = ViewModelProviderFactory(BudgetDetailsViewModel(provideBudgetDetailsInjection(context!!)))
        disposable.add(ViewModelProviders
                .of(this, factory)[BudgetDetailsViewModel::class.java]
                .budgetDetails(month(calendar[Calendar.MONTH]), calendar[Calendar.YEAR].toString())
                .subscribeOn(Schedulers.io())
                .subscribe({
                    fragmentManager.budgetDetails = it
                    fragmentManager.pieData = setData(budgetDetails = it)
                },{
                    Log.i(HomeFragment::class.java.name, it.message)
                }))
        fragmentManager.date.text = monthFormat(calendar)

        fragmentManager.decrementOnClick = object : OnClick {
            override fun onClick() {
                calendar = monthIncrementAndDecrement(calendar,-1)
                fragmentManager.date.text = monthFormat(calendar)
                disposable.add(ViewModelProviders
                        .of(this@HomeFragment, factory)[BudgetDetailsViewModel::class.java]
                        .budgetDetails(month(calendar[Calendar.MONTH]), calendar[Calendar.YEAR].toString())
                        .subscribeOn(Schedulers.io())
                        .subscribe({
                            fragmentManager.budgetDetails = it
                            fragmentManager.pieData = setData(budgetDetails = it)
                        },{
                            fragmentManager.budgetDetails = null
                            fragmentManager.pieData = null
                            Log.i(HomeFragment::class.java.name, it.message)
                        }))
            }
        }
        fragmentManager.incrementOnClick = object : OnClick {
            override fun onClick() {
                calendar = monthIncrementAndDecrement(calendar,+1)
                fragmentManager.date.text = monthFormat(calendar)
                disposable.add(ViewModelProviders
                        .of(this@HomeFragment, factory)[BudgetDetailsViewModel::class.java]
                        .budgetDetails(month(calendar[Calendar.MONTH]), calendar[Calendar.YEAR].toString())
                        .subscribeOn(Schedulers.io())
                        .subscribe({
                            fragmentManager.budgetDetails = it
                            fragmentManager.pieData = setData(budgetDetails = it)
                        },{
                            fragmentManager.budgetDetails = null
                            fragmentManager.pieData = null
                            Log.i(HomeFragment::class.java.name, it.message)
                        }))
            }
        }
        return fragmentManager.root
    }

    override fun onStop() {
        super.onStop()
        disposable.clear()
    }

    private fun setData(budgetDetails: BudgetDetails): ChartDataObject<String, PieData> {
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

        for (c in ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c)

        for (c in ColorTemplate.JOYFUL_COLORS)
            colors.add(c)

        for (c in ColorTemplate.COLORFUL_COLORS)
            colors.add(c)

        for (c in ColorTemplate.LIBERTY_COLORS)
            colors.add(c)

        for (c in ColorTemplate.PASTEL_COLORS)
            colors.add(c)

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
