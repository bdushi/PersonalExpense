package al.bruno.personal.expense.binding.utils;

import android.graphics.drawable.Drawable;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;

import al.bruno.personal.expense.SimpleItemTouchHelper;
import al.bruno.personal.expense.callback.OnSwipeItemListener;
import al.bruno.personal.expense.entities.ChartDataObject;
import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import static com.github.mikephil.charting.components.Legend.LegendHorizontalAlignment.CENTER;

public class Adapter {
    @BindingAdapter(value = {"bind:xmargin", "bind:drawableSwipeLeft", "bind:drawableSwipeRight", "bind:colorSwipeLeft", "bind:colorSwipeRight", "bind:onLeftSwipeItemListener", "bind:onRightSwipeItemListener"}, requireAll = false)
    public static void swipeItem(RecyclerView recyclerView, float xmargin, Drawable drawableSwipeLeft, Drawable drawableSwipeRight, int colorSwipeLeft, int colorSwipeRight, OnSwipeItemListener onLeftSwipeItemListener, OnSwipeItemListener onRightSwipeItemListener) {
        new ItemTouchHelper(new SimpleItemTouchHelper
                .Builder()
                .setXMarkMargin(xmargin)
                .setLeftToRightColor(colorSwipeRight)
                .setLeftToRightIcon(drawableSwipeRight)

                .setRightToLeftColor(colorSwipeLeft)
                .setRightToLeftIcon(drawableSwipeLeft)
                .build()
                .onLeftSwipeItemListener(onLeftSwipeItemListener)
                .onRightSwipeItemListener(onRightSwipeItemListener))
                .attachToRecyclerView(recyclerView);
    }

    @BindingAdapter("bind:pieData")
    public static void setData(PieChart pieChart, ChartDataObject<String, PieData> pieData) {
        if(pieData != null) {
            pieChart.getDescription().setText(pieData.getLabel());
            pieChart.setRotationEnabled(false);
            pieChart.setDrawEntryLabels(false);
            Legend legend = pieChart.getLegend();
            legend.setHorizontalAlignment(CENTER);
            legend.setFormSize(8f);
            legend.setXEntrySpace(3f);
            legend.setWordWrapEnabled(true);
            pieChart.setData(pieData.getData());
            pieChart.invalidate();
        } else {
            pieChart.setData(null);
            pieChart.invalidate();
            pieChart.setNoDataText("Nuk ka te dhena per kete raport");
        }
    }
}
