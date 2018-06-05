package al.edu.feut.financaime;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import al.edu.feut.financaime.model.Database;
import al.edu.feut.financaime.model.Expense;
import al.edu.feut.financaime.util.Utilities;

public class StatisticsFragment extends Fragment {
    private Calendar calendar = Calendar.getInstance();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_statistics, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        BarChart barChart = view.findViewById(R.id.chart);
        onChanged(new Database(getContext()).expense(Utilities.month(calendar.get(Calendar.MONTH)), String.valueOf(calendar.get(Calendar.YEAR))), barChart);

        AppCompatTextView date = view.findViewById(R.id.date);
        date.setText(Utilities.monthFormat(calendar));
        view.findViewById(R.id.decrement).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar = Utilities.monthIncrementAndDecrement(calendar,-1);
                date.setText(Utilities.monthFormat(calendar));
                onChanged(new Database(getContext()).expense(Utilities.month(calendar.get(Calendar.MONTH)), String.valueOf(calendar.get(Calendar.YEAR))), barChart);
            }
        });
        view.findViewById(R.id.increment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar = Utilities.monthIncrementAndDecrement(calendar,+1);
                date.setText(Utilities.monthFormat(calendar));
                onChanged(new Database(getContext()).expense(Utilities.month(calendar.get(Calendar.MONTH)), String.valueOf(calendar.get(Calendar.YEAR))), barChart);
            }
        });
    }

    public void onChanged(@Nullable List<Expense> expenses, BarChart barChart) {
        if (expenses != null) {
            if (!expenses.isEmpty()) {
                BarData barData = new BarData(barDataSets(expenses));
                barChart.setData(barData);
                barChart.animateXY(2000, 2000);
                barChart.getDescription().setText(getString(R.string.app_name));
                barChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(getXAxisValues(expenses)));
                barChart.invalidate();
            }
            else {
                barChart.setData(null);
                barChart.invalidate();
            }
        }
    }

    private List<IBarDataSet> barDataSets(List<Expense> expenses)
    {
        List<BarEntry> barEntryList = new ArrayList<>();
        int i = 1;
        for (Expense expense : expenses)
        {
            BarEntry barEntry = new BarEntry(i, (float) expense.getExpense());
            barEntryList.add(barEntry);
            i++;
        }
        BarDataSet barDataSet = new BarDataSet(barEntryList, Utilities.getMonth(Utilities.month(expenses.get(0).getDate())));
        barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        List<IBarDataSet> dataSets = new ArrayList<>();
        dataSets.add(barDataSet);
        return dataSets;
    }

    private String[] getXAxisValues(List<Expense> expenses)
    {
        String[] xAxis = new String[expenses.size()];
        for (int i = 0; i < expenses.size(); i++)
        {
            xAxis[i] = expenses.get(i).getExpenseName();
        }
        return xAxis;
    }
}
