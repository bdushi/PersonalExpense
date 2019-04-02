package al.bruno.personal.expense.binding.utils;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.squareup.picasso.Picasso;

import java.util.List;

import al.bruno.personal.expense.R;
import al.bruno.personal.expense.widget.helper.SimpleItemTouchHelper;
import al.bruno.personal.expense.callback.OnSwipeItemListener;
import al.bruno.personal.expense.entities.ChartDataObject;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

public class Adapter {
    @BindingAdapter(value = {"bind:xMargin", "bind:drawableSwipeLeft", "bind:drawableSwipeRight", "bind:colorSwipeLeft", "bind:colorSwipeRight", "bind:onLeftSwipeItemListener", "bind:onRightSwipeItemListener"}, requireAll = false)
    public static void swipeItem(RecyclerView recyclerView, float xMargin, Drawable drawableSwipeLeft, Drawable drawableSwipeRight, int colorSwipeLeft, int colorSwipeRight, OnSwipeItemListener onLeftSwipeItemListener, OnSwipeItemListener onRightSwipeItemListener) {
        new ItemTouchHelper(new SimpleItemTouchHelper
                .Builder()
                .setXMarkMargin(xMargin)
                .setLeftToRightColor(colorSwipeRight)
                .setLeftToRightIcon(drawableSwipeRight)

                .setRightToLeftColor(colorSwipeLeft)
                .setRightToLeftIcon(drawableSwipeLeft)
                .build()
                .onLeftSwipeItemListener(onLeftSwipeItemListener)
                .onRightSwipeItemListener(onRightSwipeItemListener))
                .attachToRecyclerView(recyclerView);
    }

    @BindingAdapter("bind:lineChart")
    public static void setLineChart(LineChart lineChart, ChartDataObject<List<String>, LineData> chartData) {
        if(chartData != null) {
            lineChart.setHorizontalScrollBarEnabled(true);
            lineChart.setPinchZoom(true);
            lineChart.getAxis(YAxis.AxisDependency.LEFT).setAxisLineColor(Color.BLUE);
            lineChart.getAxisRight().setEnabled(false); // no right axis
            //lineChart.setNoDataText(getString(R.string.no_data));
            //lineChart.visibility = View.VISIBLE

            //Enable legend - Nuk mund te vendoset pershkrim ne grafik por
            lineChart.getLegend().setEnabled(true);
            lineChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
            lineChart.getXAxis().setAvoidFirstLastClipping(false);
            // Line chart Description
            lineChart.getDescription().setText(lineChart.getContext().getString(R.string.app_name));
            //return chartData.getLabel().get((int) value);
            lineChart.getXAxis().setValueFormatter(new ValueFormatter() {
                @Override
                public String getFormattedValue(float value) {
                    return chartData.getLabel().get((int) value);
                }
            });
            lineChart.setMaxVisibleValueCount(chartData.getLabel().size());
            lineChart.setData(chartData.getData());
            lineChart.invalidate();
        } else {
            lineChart.setData(null);
            lineChart.invalidate();
            lineChart.setNoDataText("Nuk ka te dhena per kete raport");
        }
    }

    @BindingAdapter("bind:barChart")
    public static void setBarChart(BarChart barChart, ChartDataObject<IndexAxisValueFormatter, BarData> chartData) {
        if(chartData != null) {
            barChart.setData(chartData.getData());
            barChart.animateXY(1000, 1000);
            barChart.getDescription().setText(barChart.getContext().getString(R.string.app_name));
            barChart.getXAxis().setValueFormatter(chartData.getLabel());
            barChart.invalidate();
        } else {
            barChart.setData(null);
            barChart.invalidate();
            barChart.setNoDataText("Nuk ka te dhena per kete raport");
        }
    }

    @BindingAdapter("bind:image")
    public static void imageProvider(AppCompatImageView photoProfile, Uri photoUrl) {
        Picasso.get().load(photoUrl).into(photoProfile);
    }
}
