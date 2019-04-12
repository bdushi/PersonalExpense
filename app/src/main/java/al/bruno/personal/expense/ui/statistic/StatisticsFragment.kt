package al.bruno.personal.expense.ui.statistic

import al.bruno.personal.expense.R
import al.bruno.personal.expense.databinding.FragmentStatisticsBinding
import al.bruno.personal.expense.entities.ChartDataObject
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet
import com.github.mikephil.charting.utils.ColorTemplate

import al.bruno.personal.expense.model.Expense
import al.bruno.personal.expense.observer.Observer
import al.bruno.personal.expense.ui.MyRxBus
import al.bruno.personal.expense.util.Utilities.month
import android.content.Context
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import dagger.android.support.AndroidSupportInjection
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.*
import java.util.Calendar.getInstance
import java.util.concurrent.TimeUnit
import java.util.function.Consumer
import javax.inject.Inject

class StatisticsFragment : Fragment() {
    private val disposable : CompositeDisposable = CompositeDisposable()
    private var fragmentStatisticsBinding: FragmentStatisticsBinding? = null
    @Inject
    lateinit var mViewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var myRxBus: MyRxBus

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        AndroidSupportInjection.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        fragmentStatisticsBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_statistics, container, false)
        return fragmentStatisticsBinding?.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        disposable.add(ViewModelProviders
                .of(this@StatisticsFragment, mViewModelFactory)[StatisticViewModel::class.java]
                .statistics(month(getInstance().get(Calendar.MONTH)), getInstance().get(Calendar.YEAR).toString())
                .delay(50, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .subscribe({
                    fragmentStatisticsBinding!!.chartData = barData(it, getInstance().timeInMillis)
                },{
                    Log.i(StatisticsFragment::class.java.name, it.message)
                }))
        disposable.add(
                myRxBus.getEvents().subscribe({
                    disposable.add(ViewModelProviders
                            .of(this@StatisticsFragment, mViewModelFactory)[StatisticViewModel::class.java]
                            .statistics(month(it.calendar().get(Calendar.MONTH)), it.calendar().get(Calendar.YEAR).toString())
                            .delay(50, TimeUnit.MILLISECONDS)
                            .subscribeOn(Schedulers.io())
                            .subscribe({ expense ->
                                fragmentStatisticsBinding!!.chartData = barData(expense, it.calendar().timeInMillis)
                            },{
                                Log.i(StatisticsFragment::class.java.name, it.message)
                            }))
                }, {
                    Log.i(StatisticsFragment::class.java.name, it.message)
                })
        )
    }

    override fun onStop() {
        super.onStop()
        disposable.clear()
    }

    private fun barData(expenses: List<Expense>, date: Long): ChartDataObject<IndexAxisValueFormatter, BarData> {
        val barEntryList = ArrayList<BarEntry>()
        val xAxis = ArrayList<String>()
        for (i in expenses.indices) {
            barEntryList.add(BarEntry(i.toFloat(), expenses[i].amount.toFloat()))
            xAxis.add(expenses[i].category!!)
        }
        val barDataSet = BarDataSet(barEntryList, month(date))
        barDataSet.setColors(*ColorTemplate.COLORFUL_COLORS)
        val dataSets = ArrayList<IBarDataSet>()
        dataSets.add(barDataSet)
        return ChartDataObject(IndexAxisValueFormatter(xAxis), BarData(dataSets))
    }

}
