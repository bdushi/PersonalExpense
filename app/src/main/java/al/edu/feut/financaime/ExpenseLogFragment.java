package al.edu.feut.financaime;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import al.edu.feut.financaime.util.Utilities;

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
        expenseLog.setLayoutManager(new LinearLayoutManager(getActivity()));
        expenseLog.setItemAnimator(new DefaultItemAnimator());
        expenseLog.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        Calendar calendar = date();
        ExpenseMaster expenseMaster = new Database(getContext()).expenseMaster(String.valueOf(calendar.get(Calendar.DATE)), String.valueOf(calendar.get(Calendar.MONTH)),String.valueOf(calendar.get(Calendar.YEAR)));
        ExpenseAdapter expenseAdapter = new ExpenseAdapter(expenseMaster.getExpenses(), R.layout.expense_single_item);
        MaterialCalendarView expenseLogCalendarView = view.findViewById(R.id.expense_log_calendar_view);
        expenseLogCalendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {

            }
        });
    }
}
