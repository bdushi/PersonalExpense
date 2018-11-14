package al.bruno.financaime

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.appcompat.widget.AppCompatTextView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.IValueFormatter
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.utils.ColorTemplate
import com.github.mikephil.charting.utils.MPPointF

import java.text.DecimalFormat
import java.util.ArrayList
import java.util.Calendar

import al.bruno.financaime.model.BudgetDetails
import al.bruno.financaime.util.Utilities

class HomeFragment : Fragment() {
    private val calendar = Calendar.getInstance()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //date.text = Utilities.monthFormat(calendar)
        /*BudgetDetails budgetDetails =
                new Database(getContext()).budgetMaster(Utilities.newInstance.month(calendar.get(Calendar.MONTH)), String.valueOf(calendar.get(Calendar.YEAR)));*/

        /*view.findViewById(R.id.decrement).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar = Utilities.newInstance.monthIncrementAndDecrement(calendar,-1);
                date.setText(Utilities.newInstance.monthFormat(calendar));
                BudgetDetails budgetDetails =
                        new Database(getContext()).budgetMaster(Utilities.newInstance.month(calendar.get(Calendar.MONTH)), String.valueOf(calendar.get(Calendar.YEAR)));
                setData(pieChart, budgetDetails.setDate(Utilities.newInstance.date(calendar)));
                balance.setText(Utilities.newInstance.format(budgetDetails.getBalance()));
                remaining.setText(Utilities.newInstance.format(budgetDetails.getBudget()));
                expense.setText(Utilities.newInstance.format(budgetDetails.getExpense()));
                incomes.setText(Utilities.newInstance.format(budgetDetails.getIncomes()));
            }
        });
        view.findViewById(R.id.increment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar = Utilities.newInstance.monthIncrementAndDecrement(calendar,+1);
                date.setText(Utilities.newInstance.monthFormat(calendar));
                BudgetDetails budgetDetails =
                        new Database(getContext()).budgetMaster(Utilities.newInstance.month(calendar.get(Calendar.MONTH)), String.valueOf(calendar.get(Calendar.YEAR)));
                setData(pieChart, budgetDetails.setDate(Utilities.newInstance.date(calendar)));
                balance.setText(Utilities.newInstance.format(budgetDetails.getBalance()));
                remaining.setText(Utilities.newInstance.format(budgetDetails.getBudget()));
                expense.setText(Utilities.newInstance.format(budgetDetails.getExpense()));
                incomes.setText(Utilities.newInstance.format(budgetDetails.getIncomes()));
            }
        });
        setData(pieChart, budgetDetails.setDate(Utilities.newInstance.date(calendar)));
        balance.setText(Utilities.newInstance.format(budgetDetails.getBalance()));
        remaining.setText(Utilities.newInstance.format(budgetDetails.getBudget()));
        expense.setText(Utilities.newInstance.format(budgetDetails.getExpense()));
        incomes.setText(Utilities.newInstance.format(budgetDetails.getIncomes()));*/
    }

    private fun setData(mChart: PieChart, budgetDetails: BudgetDetails) {
        val entries = ArrayList<PieEntry>()
        entries.add(PieEntry((budgetDetails.expense / budgetDetails.incomes) * 100, getString(R.string.expense)))
        entries.add(PieEntry((budgetDetails.balance / budgetDetails.incomes) * 100, getString(R.string.balance)))
        val dataSet = PieDataSet(entries, "")

        val format = DecimalFormat("### %")
        dataSet.setDrawIcons(false)
        dataSet.sliceSpace = 3f
        dataSet.iconsOffset = MPPointF(0f, 40f)
        dataSet.selectionShift = 5f
        dataSet.valueFormatter = IValueFormatter { value, entry, dataSetIndex, viewPortHandler -> format.format(value.toDouble()) }

        val colors = ArrayList<Int>()

        for (c in ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c)

        for (c in ColorTemplate.JOYFUL_COLORS)
            colors.add(c)

        for (c in ColorTemplate.COLORFUL_COLORS)
            colors.add(c)

        for (c in ColorTemplate.LIBERTY_COLORS)
            colors.add(c)

        for (c in ColorTemplate.PASTEL_COLORS)
            colors.add(c)

        colors.add(ColorTemplate.getHoloBlue())

        dataSet.colors = colors
        dataSet.selectionShift = 0f

        val data = PieData(dataSet)
        data.setValueFormatter(PercentFormatter())
        data.setValueTextSize(11f)
        data.setValueTextColor(Color.WHITE)
        mChart.description.text = getString(R.string.app_name)
        mChart.data = data
        mChart.invalidate()
    }
}
