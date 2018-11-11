package al.bruno.financaime

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import al.bruno.financaime.databinding.FragmentBudgetBinding
import al.bruno.financaime.util.Utilities.month
import al.bruno.financaime.view.model.BudgetViewModel
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders

class BudgetFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        //log.setAdapter(new CustomAdapter<Expense, LogSingleItemBinding>(new Database(getContext()).expense(Utilities.INSTANCE.month(Utilities.INSTANCE.month())), R.layout.log_single_item, (value, logSingleItemBinding) -> logSingleItemBinding.setValue(value)));
        val fragmentBudgetBinding: FragmentBudgetBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_budget, container, false)
        ViewModelProviders.of(this).get(BudgetViewModel::class.java).budget(month(month())).observe(this, Observer {
            run {
                fragmentBudgetBinding.budget = it
            }
        })

        return fragmentBudgetBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //log.setAdapter(new CustomAdapter<Expense, LogSingleItemBinding>(new Database(getContext()).expense(Utilities.INSTANCE.month(Utilities.INSTANCE.month())), R.layout.log_single_item, (value, logSingleItemBinding) -> logSingleItemBinding.setValue(value)));
    }
}
