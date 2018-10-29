package al.bruno.financaime.util

import java.util.Date

import androidx.room.TypeConverter
import androidx.room.TypeConverters

object Converter {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return if (value == null) null else Date(value)
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }
}
