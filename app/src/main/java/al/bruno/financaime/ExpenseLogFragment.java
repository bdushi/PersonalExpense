package al.bruno.financaime;

import android.os.Bundle;
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

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import al.bruno.financaime.adapter.ExpenseAdapter;
import al.bruno.financaime.model.Category;
import al.bruno.financaime.model.Database;
import al.bruno.financaime.model.Expense;
import al.bruno.financaime.model.ExpenseMaster;
import al.bruno.financaime.util.EventDecorator;
import al.bruno.financaime.util.Utilities;

import static al.bruno.financaime.util.Utilities.calendar;
import static al.bruno.financaime.util.Utilities.date;

public class ExpenseLogFragment extends Fragment {

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
        expenseLog.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        new Handler().postDelayed(() -> {
            ExpenseMaster expenseMaster = new Database(getContext()).expenseMaster(calendar().getTimeInMillis());
            expenseLog.setAdapter(new ExpenseAdapter(expenseMaster.getExpenses(), R.layout.expense_single_item));
            if(expenseMaster.getTotal().equals("0"))
                view.findViewById(R.id.total_layout).setVisibility(View.GONE);
            else {
                view.findViewById(R.id.total_layout).setVisibility(View.VISIBLE);
                total.setText(expenseMaster.getTotal());
            }
            expenseLogCalendarView.addDecorator(new EventDecorator(R.color.red_a700, new Database(getActivity()).date()));
        }, 1000);

        expenseLogCalendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                new Handler().postDelayed(() -> {
                    ExpenseMaster expenseMaster = new Database(getContext()).expenseMaster(calendar(date).getTimeInMillis());
                    expenseLog.setAdapter(new ExpenseAdapter(expenseMaster.getExpenses(), R.layout.expense_single_item));
                    if(expenseMaster.getTotal().equals("0"))
                        view.findViewById(R.id.total_layout).setVisibility(View.GONE);
                    else {
                        view.findViewById(R.id.total_layout).setVisibility(View.VISIBLE);
                        total.setText(expenseMaster.getTotal());
                    }
                }, 1000);
            }
        });
    }
}
