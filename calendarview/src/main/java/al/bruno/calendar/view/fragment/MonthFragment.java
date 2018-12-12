package al.bruno.calendar.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import org.joda.time.LocalDate;

import al.bruno.calendar.view.R;
import al.bruno.calendar.view.adapter.CustomArrayAdapter;
import al.bruno.calendar.view.databinding.ControlCalendarDayBinding;
import al.bruno.calendar.view.databinding.FragmentMonthBinding;
import al.bruno.calendar.view.model.LocalDateTime;
import al.bruno.calendar.view.observer.Observer;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

public class MonthFragment extends Fragment implements Observer<LocalDate[]> {
    private static final String LOCAL_DATE_TIME = "LOCAL_DATE_TIME";
    private LocalDateTime[] localDateTimes;

    @Override
    public void update(LocalDate[] dateTimes) {
        if(localDateTimes != null && dateTimes != null) {
            for (LocalDateTime localDateTime : localDateTimes) {
                for (LocalDate dateTime : dateTimes) {
                    if (dateTime.isEqual(localDateTime.date()))
                        localDateTime.setEvent(true);
                }
            }
        }
    }

    public static class Builder {
        private LocalDateTime[] localDateTimes;
        public MonthFragment.Builder setLocalDateTimes(LocalDateTime[] localDateTimes) {
            this.localDateTimes = localDateTimes;
            return this;
        }
        public MonthFragment build() {
            return newInstance(localDateTimes);
        }
    }

    private static MonthFragment newInstance(LocalDateTime[] localDateTimes) {
        Bundle args = new Bundle();
        args.putParcelableArray(LOCAL_DATE_TIME, localDateTimes);
        MonthFragment fragment = new MonthFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
        if(getArguments() != null) {
            localDateTimes = (LocalDateTime[]) getArguments().getParcelableArray(LOCAL_DATE_TIME);
        } else
            throw new IllegalArgumentException("null argument");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentMonthBinding fragmentMonthBinding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.fragment_month, container, false);
        if(localDateTimes != null) {
            fragmentMonthBinding.setAdapter(new CustomArrayAdapter<LocalDateTime, ControlCalendarDayBinding>(localDateTimes, R.layout.control_calendar_day, (localDateTime, controlCalendarDayBinding) -> controlCalendarDayBinding.setLocalDateTime(localDateTime)));
        } else {
            throw new IllegalArgumentException("null argument");
        }
        return fragmentMonthBinding.getRoot();
    }
}
