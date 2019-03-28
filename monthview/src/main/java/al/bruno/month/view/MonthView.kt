package al.bruno.month.view

import al.bruno.adapter.BindingData
import al.bruno.adapter.CustomArrayAdapter
import al.bruno.month.view.databinding.FragmentMonthNavigationBinding
import al.bruno.month.view.databinding.MonthNavigationSingleItemBinding
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import java.util.*
import androidx.fragment.app.Fragment
import org.joda.time.format.DateTimeFormat

class MonthView : Fragment() {
    private var calendar = Calendar.getInstance()
    private var onEditListener: OnEditListener<Month>? = null

    public class Builder {
        private var calendar: Calendar? = null
        fun setCalendar(calendar: Calendar): Builder {
            this.calendar = calendar
            return this
        }
        fun build() : MonthView {
            return newInstance(calendar = calendar!!)
        }
    }
    companion object {
        private fun newInstance(calendar: Calendar): MonthView {
            val bundle = Bundle()
            bundle.putLong("CALENDAR", calendar.timeInMillis)
            val fragment = MonthView()
            fragment.arguments = bundle
            return fragment
        }
    }

    fun setOnEditListener(onEditListener: OnEditListener<Month>): MonthView {
        this.onEditListener = onEditListener
        return this
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val fragmentMonthNavigationBinding: FragmentMonthNavigationBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_month_navigation, container, false)
        val customArrayAdapter = CustomArrayAdapter(
                Array(12) {
                    calendar.set(Calendar.MONTH, it)
                    Month(calendar.timeInMillis)
                },
                R.layout.month_navigation_single_item,
                object : BindingData<Month, MonthNavigationSingleItemBinding> {
                    override fun bindData(t: Month, vm: MonthNavigationSingleItemBinding) {
                        vm.month = t
                        vm.onEditListener = onEditListener
                    }

                }

        )

        fragmentMonthNavigationBinding.adapter = customArrayAdapter
        fragmentMonthNavigationBinding.date.text = year(calendar.timeInMillis)
        fragmentMonthNavigationBinding.decrementOnClick = object : OnClick {
            override fun onClick() {
                calendar.add(Calendar.YEAR, -1)
                customArrayAdapter.setT(Array(12) {
                    calendar.set(Calendar.MONTH, it)
                    Month(calendar.timeInMillis)
                })
                fragmentMonthNavigationBinding.date.text = year(calendar.timeInMillis)
            }

        }
        fragmentMonthNavigationBinding.incrementOnClick = object : OnClick {
            override fun onClick() {
                calendar.add(Calendar.YEAR, +1)
                customArrayAdapter.setT(Array(12) {
                    calendar.set(Calendar.MONTH, it)
                    Month(calendar.timeInMillis)
                })
                fragmentMonthNavigationBinding.date.text = year(calendar.timeInMillis)
            }
        }
        return fragmentMonthNavigationBinding.root
    }

    fun year(date: Long): String {
        return DateTimeFormat.forPattern("yyyy").print(date)
    }
}
