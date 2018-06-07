package al.edu.feut.financaime;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.MPPointF;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import al.edu.feut.financaime.model.Budget;
import al.edu.feut.financaime.model.BudgetMaster;
import al.edu.feut.financaime.model.Database;
import al.edu.feut.financaime.util.Utilities;

import static al.edu.feut.financaime.util.Utilities.monthFormat;
import static al.edu.feut.financaime.util.Utilities.monthIncrementAndDecrement;

public class HomeFragment extends Fragment {
    private Calendar calendar = Calendar.getInstance();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        AppCompatTextView date = view.findViewById(R.id.date);

        AppCompatTextView balance = view.findViewById(R.id.balance);
        AppCompatTextView remaining = view.findViewById(R.id.remaining);
        AppCompatTextView expense = view.findViewById(R.id.expense);
        AppCompatTextView incomes = view.findViewById(R.id.incomes);

        PieChart pieChart = view.findViewById(R.id.pie_chart);
        date.setText(monthFormat(calendar));
        BudgetMaster budgetMaster =
                new Database(getContext()).budgetMaster(Utilities.month(calendar.get(Calendar.MONTH)), String.valueOf(calendar.get(Calendar.YEAR)));
        view.findViewById(R.id.decrement).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar = monthIncrementAndDecrement(calendar,-1);
                date.setText(monthFormat(calendar));
                BudgetMaster budgetMaster =
                        new Database(getContext()).budgetMaster(Utilities.month(calendar.get(Calendar.MONTH)), String.valueOf(calendar.get(Calendar.YEAR)));
                setData(pieChart, budgetMaster.setDate(Utilities.date(calendar)));
                balance.setText(Utilities.format(budgetMaster.getBalance()));
                remaining.setText(Utilities.format(budgetMaster.getBudget()));
                expense.setText(Utilities.format(budgetMaster.getExpense()));
                incomes.setText(Utilities.format(budgetMaster.getIncomes()));
            }
        });
        view.findViewById(R.id.increment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar = monthIncrementAndDecrement(calendar,+1);
                date.setText(monthFormat(calendar));
                BudgetMaster budgetMaster =
                        new Database(getContext()).budgetMaster(Utilities.month(calendar.get(Calendar.MONTH)), String.valueOf(calendar.get(Calendar.YEAR)));
                setData(pieChart, budgetMaster.setDate(Utilities.date(calendar)));
                balance.setText(Utilities.format(budgetMaster.getBalance()));
                remaining.setText(Utilities.format(budgetMaster.getBudget()));
                expense.setText(Utilities.format(budgetMaster.getExpense()));
                incomes.setText(Utilities.format(budgetMaster.getIncomes()));
            }
        });
        setData(pieChart, budgetMaster.setDate(Utilities.date(calendar)));
        balance.setText(Utilities.format(budgetMaster.getBalance()));
        remaining.setText(Utilities.format(budgetMaster.getBudget()));
        expense.setText(Utilities.format(budgetMaster.getExpense()));
        incomes.setText(Utilities.format(budgetMaster.getIncomes()));
    }

    private void setData(PieChart mChart, BudgetMaster budgetMaster) {
        List<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry((float) (budgetMaster.getExpense() / budgetMaster.getIncomes()) * 100, getString(R.string.expense)));
        entries.add(new PieEntry((float) (budgetMaster.getBalance() / budgetMaster.getIncomes()) * 100, getString(R.string.balance)));
        PieDataSet dataSet = new PieDataSet(entries, "");

        DecimalFormat format = new DecimalFormat("### %");
        dataSet.setDrawIcons(false);
        dataSet.setSliceSpace(3f);
        dataSet.setIconsOffset(new MPPointF(0, 40));
        dataSet.setSelectionShift(5f);
        dataSet.setValueFormatter(new IValueFormatter() {
            @Override
            public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                return format.format(value);
            }
        });

        List<Integer> colors = new ArrayList<>();

        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);

        colors.add(ColorTemplate.getHoloBlue());

        dataSet.setColors(colors);
        dataSet.setSelectionShift(0f);

        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.WHITE);
        mChart.getDescription().setText(getString(R.string.app_name));
        mChart.setData(data);
        mChart.invalidate();
    }
}
