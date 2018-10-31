package al.bruno.financaime;

import android.os.Bundle;

import al.bruno.financaime.adapter.CustomAdapter;
import al.bruno.financaime.callback.BindingData;
import al.bruno.financaime.databinding.ExpenseSingleItemBinding;
import al.bruno.financaime.model.Expense;
import al.bruno.financaime.util.Utilities;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import al.bruno.financaime.data.source.local.Database;
import al.bruno.financaime.model.ExpenseDetails;
import al.bruno.financaime.util.EventDecorator;

public class DetailsFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_log_expense, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView expenseLog = view.findViewById(R.id.expense_log);
        AppCompatTextView total = view.findViewById(R.id.total);
        MaterialCalendarView expenseLogCalendarView = view.findViewById(R.id.expense_log_calendar_view);

        expenseLog.setLayoutManager(new LinearLayoutManager(getActivity()));
        expenseLog.setItemAnimator(new DefaultItemAnimator());
        expenseLog.addItemDecoration(new DividerItemDecoration(Objects.requireNonNull(getActivity()), LinearLayoutManager.VERTICAL));

        new Handler().post(() -> {
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
        });

        expenseLogCalendarView.setOnDateChangedListener((widget, date, selected) -> new Handler().post(() -> {
            ExpenseDetails expenseDetails = new Database(getContext()).expenseMaster(Utilities.INSTANCE.calendar(date).getTimeInMillis());
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
        }));
    }
}
