package al.bruno.personal.expense

import al.bruno.calendar.view.adapter.BindingInterface
import al.bruno.calendar.view.adapter.CustomArrayAdapter
import al.bruno.personal.expense.callback.OnClick
import al.bruno.personal.expense.callback.OnEditListener
import al.bruno.personal.expense.databinding.FragmentMonthNavigationBinding
import al.bruno.personal.expense.databinding.MonthNavigationSingleItemBinding
import al.bruno.personal.expense.entities.Month
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
    private var onEditListener: OnEditListener<Month>? = null

    public class Builder {
        private var calendar: Calendar? = null
        fun setCalendar(calendar: Calendar): MonthNavigationFragment.Builder {
            this.calendar = calendar
            return this
        }
        fun build() : MonthNavigationFragment {
            return newInstance(calendar = calendar!!)
        }
    }
    companion object {
        private fun newInstance(calendar: Calendar): MonthNavigationFragment {
            val bundle = Bundle()
            bundle.putLong("CALENDAR", calendar.timeInMillis)
            val fragment = MonthNavigationFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    fun setOnEditListener(onEditListener: OnEditListener<Month>): MonthNavigationFragment {
        this.onEditListener = onEditListener
        return this
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val fragmentMonthNavigationBinding: FragmentMonthNavigationBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_month_navigation, container, false)
        val customArrayAdapter = CustomArrayAdapter<Month, MonthNavigationSingleItemBinding>(
                Array(12) {
                    calendar.set(Calendar.MONTH, it)
                    Month(calendar.timeInMillis)
                },
                R.layout.month_navigation_single_item,
                BindingInterface<Month, MonthNavigationSingleItemBinding> { t, vm ->
                    vm.month = t
                    vm.onEditListener = onEditListener
                })

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
}
