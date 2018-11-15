package al.bruno.financaime.binding.utils;

import android.graphics.drawable.Drawable;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;

import al.bruno.financaime.SimpleItemTouchHelper;
import al.bruno.financaime.callback.OnSwipeItemListener;
import al.bruno.financaime.entities.PieDataObject;
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
                .setLeftToRightIcon(drawableSwipeLeft)
                .setRightToLeftColor(colorSwipeLeft)
                .setRightToLeftIcon(drawableSwipeRight)
                .build()
                .onLeftSwipeItemListener(onLeftSwipeItemListener)
                .onRightSwipeItemListener(onRightSwipeItemListener))
                .attachToRecyclerView(recyclerView);
    }

    @BindingAdapter("bind:pieData")
    public static void setData(PieChart pieChart, PieDataObject<String, PieData> pieData) {
        if(pieData != null) {
            pieChart.getDescription().setText(pieData.getId());
            pieChart.setRotationEnabled(false);
            pieChart.setDrawEntryLabels(false);
            Legend legend = pieChart.getLegend();
            legend.setHorizontalAlignment(CENTER);
            legend.setFormSize(8f);
            legend.setXEntrySpace(3f);
            legend.setWordWrapEnabled(true);
            pieChart.setData(pieData.getPieData());
            pieChart.invalidate();
        } else {
            pieChart.setNoDataText("Nuk ka te dhena per kete raport");
        }
    }
}
