package al.bruno.month.view

import org.joda.time.format.DateTimeFormat
import java.text.SimpleDateFormat
import java.util.*

class Month (timeInMillis: Long) {
    private var calendar: Calendar = Calendar.getInstance()
    fun month(): String {
        return SimpleDateFormat("MMM", Locale.getDefault()).format((calendar.timeInMillis))
    }
    fun date(): String {
        return SimpleDateFormat("dd MMM YYYY", Locale.getDefault()).format((calendar.timeInMillis))
    }
    fun monthFormat(): String {
        return DateTimeFormat.forPattern("MMMM yyyy").print(calendar.timeInMillis)
    }
    fun calendar(): Calendar {
        return calendar
    }

    override fun toString(): String {
        return date()
    }

    init {
        calendar.timeInMillis = timeInMillis
    }
}