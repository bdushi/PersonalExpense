package al.bruno.calendar.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import org.joda.time.DateTime;

import al.bruno.calendar.view.databinding.CalendarViewBinding;
import al.bruno.calendar.view.listener.OnDateClickListener;
import al.bruno.calendar.view.model.Calendar;
import al.bruno.calendar.view.model.LocalDateTime;
import androidx.annotation.NonNull;
import androidx.databinding.BindingAdapter;
import androidx.databinding.DataBindingUtil;

public class CalendarView extends LinearLayout implements OnDateClickListener{
	//event handling
	private OnDateClickListener onDateClickListener;
	//Calendar Model
	private Calendar calendar;

	public CalendarView(Context context) {
		super(context);
		initControl(context);
	}

	public CalendarView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initControl(context);
	}

	public CalendarView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		initControl(context);
	}

	private void initControl(Context context) {
		CalendarViewBinding calendarViewBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.calendar_view, this, true);
		calendar = new Calendar(context, DateTime.now().withTime(0, 0, 0, 0)).setOnDateClickListener(this);
		new android.os.Handler().postDelayed(() -> calendarViewBinding.setCalendar(calendar), 100);
	}

	@BindingAdapter(value = {"app:onDateClickListener"}, requireAll = false)
    public static void setOnDateClickListener(@NonNull CalendarView calendarView, @NonNull OnDateClickListener onDateClickListener) {
	    calendarView.setOnDateClickListener(onDateClickListener);
    }

	public void setOnDateClickListener(OnDateClickListener onDateClickListener) {
		this.onDateClickListener = onDateClickListener;
	}

	@BindingAdapter(value = {"app:event"}, requireAll = false)
	public static void addEvent(@NonNull CalendarView calendarView, @NonNull DateTime[] dateTimeEvent) {
		calendarView.addEvent(dateTimeEvent);
    }

	public void addEvent(@NonNull DateTime[] dateTimeEvent) {
		calendar.setEvent(dateTimeEvent);
	}

	@Override
	public void setOnDateClickListener(View view, LocalDateTime localDateTime) {
		onDateClickListener.setOnDateClickListener(view, localDateTime);
	}
}
