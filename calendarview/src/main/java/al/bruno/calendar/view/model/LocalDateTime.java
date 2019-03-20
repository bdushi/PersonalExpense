package al.bruno.calendar.view.model;

import al.bruno.calendar.view.BR;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.View;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;

import al.bruno.calendar.view.listener.OnDateClickListener;
import androidx.databinding.Bindable;
import androidx.databinding.Observable;
import androidx.databinding.PropertyChangeRegistry;

public class LocalDateTime implements OnDateClickListener, Observable, Parcelable {
    private DateTime dateTime;
    private DateTime currentDateTime = DateTime.now().withTime(0, 0, 0, 0);
    private boolean event;
    private PropertyChangeRegistry propertyChangeRegistry = new PropertyChangeRegistry();

    private OnDateClickListener onDateClickListener;

    public LocalDateTime(DateTime dateTime, OnDateClickListener onDateClickListener) {
        this.dateTime = dateTime;
        this.onDateClickListener = onDateClickListener;
    }

    protected LocalDateTime(Parcel in) {
        event = in.readByte() != 0;
        dateTime = new DateTime(in.readLong());
        currentDateTime = new DateTime(in.readLong());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (event ? 1 : 0));
        dest.writeLong(dateTime.getMillis());
        dest.writeLong(currentDateTime.getMillis());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<LocalDateTime> CREATOR = new Creator<LocalDateTime>() {
        @Override
        public LocalDateTime createFromParcel(Parcel in) {
            return new LocalDateTime(in);
        }

        @Override
        public LocalDateTime[] newArray(int size) {
            return new LocalDateTime[size];
        }
    };

    @Bindable
    public DateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(DateTime dateTime) {
        this.dateTime = dateTime;
    }

    public LocalDate date() {
        return dateTime.toLocalDate();
    }

    public String getDate() {
        return String.valueOf(dateTime.getDayOfMonth());
    }

    @Bindable
    public boolean isEvent() {
        return event;
    }

    public void setEvent(boolean event) {
        propertyChangeRegistry.notifyChange(this, BR.event);
        this.event = event;
    }

    public boolean isSunday() {
        return dateTime.getDayOfWeek() == 7;
    }

    public boolean isToday() {
        return dateTime.getDayOfMonth() == currentDateTime.getDayOfMonth() && dateTime.getMonthOfYear() == currentDateTime.getMonthOfYear() && dateTime.getYear() == currentDateTime.getYear();
    }

    public boolean isCurrentMonth() {
        return dateTime.getMonthOfYear() == currentDateTime.getMonthOfYear() && dateTime.getYear() == currentDateTime.getYear();
    }

    @Override
    public void setOnDateClickListener(View view, LocalDateTime localDateTime) {
        onDateClickListener.setOnDateClickListener(view, localDateTime);
    }

    @Override
    public void addOnPropertyChangedCallback(OnPropertyChangedCallback callback) {
        propertyChangeRegistry.add(callback);
    }

    @Override
    public void removeOnPropertyChangedCallback(OnPropertyChangedCallback callback) {
        propertyChangeRegistry.remove(callback);
    }

}
