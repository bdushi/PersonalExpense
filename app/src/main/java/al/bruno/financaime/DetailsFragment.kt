package al.bruno.financaime

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.prolificinteractive.materialcalendarview.MaterialCalendarView

import java.util.Objects

class DetailsFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val expenseLog = view.findViewById<RecyclerView>(R.id.expense_log)
        val total = view.findViewById<AppCompatTextView>(R.id.total)
        val expenseLogCalendarView = view.findViewById<MaterialCalendarView>(R.id.expense_log_calendar_view)

        expenseLog.layoutManager = LinearLayoutManager(activity)
        expenseLog.itemAnimator = DefaultItemAnimator()
        expenseLog.addItemDecoration(DividerItemDecoration(activity, LinearLayoutManager.VERTICAL))

        /*new Handler().post(() -> {
            ExpenseDetails expenseDetails = new Database(getContext()).expenseMaster(Utilities.INSTANCE.calendar().getTimeInMillis());
            expenseLog.setAdapter(new CustomAdapter<Expense, ExpenseSingleItemBinding>(expenseDetails.getExpenses(), R.layout.expense_single_item, new BindingData<Expense, ExpenseSingleItemBinding>() {
                @Override
                public void bindData(Expense expense, @NotNull ExpenseSingleItemBinding expenseSingleItemBinding) {
                    expenseSingleItemBinding.setExpense(expense);
                }
            }));
            if(expenseDetails.getTotal().equals("0"))
                view.findViewById(R.id.total_layout).setVisibility(View.GONE);
            else {
                view.findViewById(R.id.total_layout).setVisibility(View.VISIBLE);
                total.setText(expenseDetails.getTotal());
            }
            expenseLogCalendarView.addDecorator(new EventDecorator(R.color.red_a700, new Database(getActivity()).date()));
        });*/

        expenseLogCalendarView.setOnDateChangedListener { widget, date, selected ->
            Handler().post {
                /*ExpenseDetails expenseDetails = new Database(getContext()).expenseMaster(Utilities.INSTANCE.calendar(date).getTimeInMillis());
            expenseLog.setAdapter(new CustomAdapter<Expense, ExpenseSingleItemBinding>(expenseDetails.getExpenses(), R.layout.expense_single_item, new BindingData<Expense, ExpenseSingleItemBinding>() {
                @Override
                public void bindData(Expense expense, @NotNull ExpenseSingleItemBinding expenseSingleItemBinding) {
                    expenseSingleItemBinding.setExpense(expense);
                }
            }));
            if(expenseDetails.getTotal().equals("0"))
                view.findViewById(R.id.total_layout).setVisibility(View.GONE);
            else {
                view.findViewById(R.id.total_layout).setVisibility(View.VISIBLE);
                total.setText(expenseDetails.getTotal());
            }*/
            }
        }
    }
}
