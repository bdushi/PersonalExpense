package al.edu.feut.financaime;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import al.edu.feut.financaime.adapter.ExpenseAdapter;
import al.edu.feut.financaime.model.Category;
import al.edu.feut.financaime.model.Database;
import al.edu.feut.financaime.model.Expense;
import al.edu.feut.financaime.model.ExpenseMaster;
import al.edu.feut.financaime.util.EventDecorator;
import al.edu.feut.financaime.util.Utilities;

import static al.edu.feut.financaime.util.Utilities.calendar;
import static al.edu.feut.financaime.util.Utilities.date;

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
        Calendar calendar = calendar();
        ExpenseMaster expenseMaster =
                new Database(getContext()).expenseMaster(
                        Utilities.date(calendar.get(Calendar.DATE)),
                        Utilities.month(calendar.get(Calendar.MONTH)),
                        String.valueOf(calendar.get(Calendar.YEAR)));
        expenseLog.setAdapter(new ExpenseAdapter(expenseMaster.getExpenses(), R.layout.expense_single_item));
        if(expenseMaster.getTotal().equals("0"))
            total.setVisibility(View.GONE);
        else {
            total.setVisibility(View.VISIBLE);
            total.setText(expenseMaster.getTotal());
        }
        total.setText(expenseMaster.getTotal());
        expenseLogCalendarView.addDecorator(new EventDecorator(R.color.red_a700, new Database(getActivity()).date()));
        expenseLogCalendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                ExpenseMaster expenseMaster =
                        new Database(getContext()).expenseMaster(
                                Utilities.date(date.getDay()),
                                Utilities.month(date.getMonth()),
                                String.valueOf(date.getYear()));
                expenseLog.setAdapter(new ExpenseAdapter(expenseMaster.getExpenses(), R.layout.expense_single_item));
                if(expenseMaster.getTotal().equals("0"))
                    total.setVisibility(View.GONE);
                else {
                    total.setVisibility(View.VISIBLE);
                    total.setText(expenseMaster.getTotal());
                }
            }
        });
    }
}
