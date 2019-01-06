package al.bruno.personal.expense

import al.bruno.calendar.view.adapter.BindingInterface
import al.bruno.calendar.view.adapter.CustomArrayAdapter
import al.bruno.personal.expense.callback.OnEditListener
import al.bruno.personal.expense.databinding.FragmentMonthNavigationBinding
import al.bruno.personal.expense.databinding.MonthNavigationSingleItemBinding
import al.bruno.personal.expense.util.Month
import al.bruno.personal.expense.util.MonthNavigation
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import java.util.*
import al.bruno.personal.expense.util.Utilities.year
import androidx.fragment.app.Fragment

class MonthNavigationFragment : Fragment() {
    private var calendar = Calendar.getInstance()
    private var onEditListener: OnEditListener<Calendar>? = null

    companion object {
        public class Builder {
            private var calendar: Calendar? = null
                set(value) {
                    field = value
                }
            fun build() : MonthNavigationFragment {
                return newInstance(calendar = calendar!!)
            }
        }

        private fun newInstance(calendar: Calendar): MonthNavigationFragment {
            val bundle = Bundle()
            bundle.putLong("CALENDAR", calendar.timeInMillis)
            val fragment = MonthNavigationFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    fun setOnEditListener(onEditListener: OnEditListener<Calendar>): MonthNavigationFragment {
        this.onEditListener = onEditListener
        return this
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //calendar.timeInMillis = arguments!!["CALENDAR"] as Long
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val fragmentMonthNavigationBinding: FragmentMonthNavigationBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_month_navigation, container, false)
        /*val month = Array(12) {
            calendar.set(Calendar.MONTH, it)
            Log.i(MonthNavigationFragment::class.java.name, Month(calendar = calendar).month())
            Month(calendar = calendar)}*/
        /*fragmentMonthNavigationBinding.adapter = MonthNavigationAdapter<Calendar, MonthNavigationSingleItemBinding>(calendar, R.layout.month_navigation_single_item, object : BindingData<Calendar, MonthNavigationSingleItemBinding> {
            override fun bindData(t: Calendar, vm: MonthNavigationSingleItemBinding) {
                vm.calendar = t
                vm.onEditListener = onEditListener
            }
        })*/
        val monthNavigation = MonthNavigation(calendar)
        fragmentMonthNavigationBinding.adapter =
                CustomArrayAdapter<Month, MonthNavigationSingleItemBinding>(monthNavigation.month, R.layout.month_navigation_single_item, object : BindingInterface<Month, MonthNavigationSingleItemBinding> {
                    override fun bindData(t: Month, vm: MonthNavigationSingleItemBinding) {
                        vm.calendar = t
                        vm.onEditListener = onEditListener
                    }
                })
        fragmentMonthNavigationBinding.date.text = year(calendar.timeInMillis)
        fragmentMonthNavigationBinding.decrementOnClick = object : OnClick {
            override fun onClick() {
                calendar.add(Calendar.YEAR, -1)
                fragmentMonthNavigationBinding.date.text = year(calendar.timeInMillis)
            }

        }
        fragmentMonthNavigationBinding.incrementOnClick = object : OnClick {
            override fun onClick() {
                calendar.add(Calendar.YEAR, +1)
                fragmentMonthNavigationBinding.date.text = year(calendar.timeInMillis)
            }
        }
        return fragmentMonthNavigationBinding.root
    }

}
