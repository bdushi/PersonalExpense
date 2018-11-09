package al.bruno.financaime.binding.utils;

import android.graphics.drawable.Drawable;

import al.bruno.financaime.SimpleItemTouchHelper;
import al.bruno.financaime.callback.OnSwipeItemListener;
import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

public class Adapter {
    @BindingAdapter(value = {"bind:xmargin", "bind:drawableSwipeLeft", "bind:drawableSwipeRight", "bind:colorSwipeLeft", "bind:colorSwipeRight", "bind:onSwipeItemListener"}, requireAll = false)
    public static void swipeItem(RecyclerView recyclerView, float xmargin, Drawable drawableSwipeLeft, Drawable drawableSwipeRight, int colorSwipeLeft, int colorSwipeRight, OnSwipeItemListener onSwipeItemListener) {
        new ItemTouchHelper(new SimpleItemTouchHelper
                .Builder()
                .setXMarkMargin(xmargin)
                .setLeftToRightColor(colorSwipeRight)
                .setLeftToRightIcon(drawableSwipeLeft)
                .setRightToLeftColor(colorSwipeLeft)
                .setRightToLeftIcon(drawableSwipeRight)
                .build()
                .onSwipeItemListener(onSwipeItemListener))
                .attachToRecyclerView(recyclerView);
    }
}
