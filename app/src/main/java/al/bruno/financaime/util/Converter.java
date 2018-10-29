package al.bruno.financaime.util;

import java.util.Date;

import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

public class Converter {
    @TypeConverter
    public static Date fromTimestamp(Long value) {
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public static Long dateToTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }
}
