package al.bruno.personal.expense.util

import androidx.room.TypeConverter
import org.joda.time.DateTime

class Converter {
    @TypeConverter
    fun fromTimestamp(value: Long?): DateTime? {
        return DateTime(value)
    }

    @TypeConverter
    fun dateToTimestamp(date: DateTime?): Long? {
        return date?.millis
    }
}
