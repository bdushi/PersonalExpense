package al.bruno.calendar.view.adapter;

import android.view.View;

import androidx.annotation.NonNull;

public interface OnItemClickListener<T> {
    void onItemClick(@NonNull View view, T t);
    boolean onLongItemClick(@NonNull View view, T t);
}
