package al.bruno.financaime.binding.utils;

import android.graphics.drawable.Drawable;

import al.bruno.financaime.SimpleItemTouchHelper;
import al.bruno.financaime.callback.OnSwipeItemListener;
import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

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
}
