package al.bruno.personal.expense.dialog

import al.bruno.personal.expense.R
import al.bruno.personal.expense.adapter.CustomArrayAdapter
import al.bruno.personal.expense.callback.BindingData
import al.bruno.personal.expense.databinding.FragmentMonthNavigationBinding
import al.bruno.personal.expense.databinding.MonthNavigationSingleItemBinding
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import java.util.*
import al.bruno.personal.expense.util.Utilities.year
import androidx.fragment.app.Fragment

class MonthNavigationDialog : Fragment() {

    private var calendar = Calendar.getInstance()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val fragmentMonthNavigationBinding: FragmentMonthNavigationBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_month_navigation, container, false)
        fragmentMonthNavigationBinding.adapter = CustomArrayAdapter<String, MonthNavigationSingleItemBinding>(resources.getStringArray(R.array.months), R.layout.month_navigation_single_item, object : BindingData<String, MonthNavigationSingleItemBinding> {
            override fun bindData(t: String, vm: MonthNavigationSingleItemBinding) {
                vm.month = t
            }
        })

        fragmentMonthNavigationBinding.date.text = year(calendar.timeInMillis)
        return fragmentMonthNavigationBinding.root
    }
}
