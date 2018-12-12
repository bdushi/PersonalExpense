package al.bruno.calendar.view.listener;

import android.view.View;

import al.bruno.calendar.view.model.LocalDateTime;

public interface OnDateClickListener {
    void setOnDateClickListener(View view, LocalDateTime localDateTime);
}
