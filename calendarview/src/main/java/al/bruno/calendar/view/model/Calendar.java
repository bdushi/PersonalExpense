package al.bruno.calendar.view.model;

import android.content.Context;
import android.view.LayoutInflater;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;
import al.bruno.calendar.view.BR;
import al.bruno.calendar.view.R;
import al.bruno.calendar.view.adapter.CustomArrayAdapter;
import al.bruno.calendar.view.adapter.MonthAdapter;
import al.bruno.calendar.view.fragment.MonthFragment;
import al.bruno.calendar.view.adapter.MonthPagerAdapter;
import al.bruno.calendar.view.observer.Observer;
import al.bruno.calendar.view.observer.Subject;
import al.bruno.calendar.view.util.Utilities;
import al.bruno.calendar.view.listener.OnDateClickListener;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.Observable;
import androidx.databinding.PropertyChangeRegistry;
import androidx.viewpager.widget.ViewPager;
import al.bruno.calendar.view.databinding.FragmentMonthBinding;
import al.bruno.calendar.view.databinding.ControlCalendarDayBinding;

import static al.bruno.calendar.view.util.Constants.DAYS_COUNT;
import static al.bruno.calendar.view.util.Constants.PREFILLED_MONTHS;

public class Calendar implements Observable, Subject<DateTime[]> {
    private DateTime dateTime;
    private DateTime[] dateTimes;
    private DateTime[] dateTimeEvent;
    private OnDateClickListener onDateClickListener;
    private MonthPagerAdapter monthPagerAdapter;
    private MonthAdapter monthAdapter;
    private PropertyChangeRegistry propertyChangeRegistry = new PropertyChangeRegistry();
    private List<Observer<DateTime[]>> registry = new ArrayList<>();
    /**
     * Display dates correctly in grid
     */
    public Calendar(Context context, DateTime dateTime) {
        this.dateTime = dateTime;
        this.dateTimes = months(dateTime);
        monthPagerAdapter = new MonthPagerAdapter(((AppCompatActivity)context).getSupportFragmentManager(), dateTimes.length, integer -> {
            MonthFragment monthFragment = new MonthFragment.Builder().setLocalDateTimes(Calendar.this.dateTime(dateTimes[integer], onDateClickListener)).build();
            registerObserver(monthFragment);
            return monthFragment;
        });
    }

    public Calendar(DateTime dateTime) {
        this.dateTime = dateTime;
        this.dateTimes = months(dateTime);
        monthAdapter =
                new MonthAdapter((viewGroup, position) -> {
                    FragmentMonthBinding fragmentMonth = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()), R.layout.fragment_month, viewGroup, false);
                    fragmentMonth.setAdapter(new CustomArrayAdapter<LocalDateTime, ControlCalendarDayBinding>(dateTime(dateTimes[position], onDateClickListener), R.layout.control_calendar_day, (localDateTime, controlCalendarDayBinding) -> controlCalendarDayBinding.setLocalDateTime(localDateTime)));
                    viewGroup.addView(fragmentMonth.getRoot());
                    return fragmentMonth.getRoot();
                }, dateTimes.length);
    }

    public MonthPagerAdapter getMonthPagerAdapter() {
        return monthPagerAdapter;
    }

    public MonthAdapter getMonthAdapter() {
        return monthAdapter;
    }

    public DateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(DateTime dateTime) {
        this.dateTime = dateTime;
        propertyChangeRegistry.notifyChange(this, BR.month);
        propertyChangeRegistry.notifyChange(this, BR.year);
    }

    @Bindable
    public String getMonth() {
        return Utilities.month(dateTime);
    }
    @Bindable
    public String getYear() {
        return String.format("%s", dateTime.getYear());
    }


    public Calendar setOnDateClickListener(OnDateClickListener onDateClickListener) {
        this.onDateClickListener = onDateClickListener;
        return this;
    }
    private LocalDateTime[] calendar() {
        java.util.Calendar calendar = java.util.Calendar.getInstance();
        LocalDateTime[] localDateTime = new LocalDateTime[DAYS_COUNT];
        // determine the cell for current month's beginning
        calendar.set(java.util.Calendar.DAY_OF_MONTH, 1);
        int monthBeginningCell = calendar.get(java.util.Calendar.DAY_OF_WEEK) - 2;
        // move calendar backwards to the beginning of the week
        calendar.add(java.util.Calendar.DAY_OF_MONTH, -monthBeginningCell);
        // how many days to show, defaults to six weeks, 42 days
        // (view, ld) -> onDateClickListener.setOnDateClickListener(view, ld)
        for (int i = 0; i < DAYS_COUNT; i++) {
            localDateTime[i] = new LocalDateTime(new DateTime(calendar.getTimeInMillis()), onDateClickListener);
            calendar.add(java.util.Calendar.DAY_OF_MONTH, 1);
        }
        return localDateTime;
    }
    private LocalDateTime[] dateTime(DateTime dateTime, OnDateClickListener onDateClickListener) {
        LocalDateTime[] localDateTime = new LocalDateTime[DAYS_COUNT];
        int currMonthDays = dateTime.dayOfMonth().getMaximumValue();
        int firstDayIndex = dateTime.withDayOfMonth(1).getDayOfWeek();
        int prevMonthDays = dateTime.minusMonths(1).dayOfMonth().getMaximumValue();
        int value = prevMonthDays - (firstDayIndex -= 1) + 1;
        DateTime curDay = dateTime;
        for (int i = 0; i < DAYS_COUNT; i++) {
            if(i < firstDayIndex) {
                    curDay = dateTime.withDayOfMonth(1).minusMonths(1);
            } else if(i == firstDayIndex) {
                    value = 1;
                    curDay = dateTime;
            } else if( value == currMonthDays + 1) {
                    value = 1;
                    curDay = dateTime.withDayOfMonth(1).plusMonths(1);
            }
            localDateTime[i] = new LocalDateTime(curDay.withDayOfMonth(value), onDateClickListener);
            value++;
        }
        return localDateTime;
    }

    private DateTime[] months(DateTime dateTime) {
        DateTime[] dateTimes = new DateTime[PREFILLED_MONTHS];
        int ii = 0;
        for (int i = -PREFILLED_MONTHS / 2; i < PREFILLED_MONTHS / 2; i++) {
            dateTimes[ii] = dateTime.plusMonths(i);
            ii++;
        }
        return dateTimes;
    }

    @Override
    public void addOnPropertyChangedCallback(OnPropertyChangedCallback callback) {
        propertyChangeRegistry.add(callback);
    }

    @Override
    public void removeOnPropertyChangedCallback(OnPropertyChangedCallback callback) {
        propertyChangeRegistry.remove(callback);
    }

    public ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            setDateTime(dateTimes[position]);
            notifyObserver(dateTimeEvent);
        }

        @Override
        public void onPageSelected(int position) {

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    @Override
    public void removeObserver(Observer<DateTime[]> o) {
        if (registry.indexOf(o) >= 0)
            registry.remove(o);
    }


    @Override
    public void registerObserver(Observer<DateTime[]> o) {
        registry.add(o);
    }

    @Override
    public void notifyObserver(DateTime[] dateTimes) {
        for (Observer<DateTime[]> observer : registry) {
            observer.update(dateTimes);
        }
    }

    public void setEvent(DateTime[] dateTimeEvent) {
        this.dateTimeEvent = dateTimeEvent;
    }
}
