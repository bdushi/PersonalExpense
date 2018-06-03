package al.edu.feut.financaime.util;

import java.util.Date;

public class Converters {
    public static Date fromTimestamp(Long value) {
        return value == null ? null : new Date(value);
    }
    public static Long dateToTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }

    /*@TypeConverter
    public static Integer intToBool(boolean value)
    {
        return value ? 1 : 0;
    }
    @TypeConverter
    public static Boolean fromBoolToInt(Integer value)
    {
        return value > 0;
    }*/
}

