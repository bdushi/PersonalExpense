package al.bruno.personal.expense.util

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

    public fun calendar(): Calendar {
        return calendar
    }

    override fun toString(): String {
        return date()
    }

    init {
        calendar.timeInMillis = timeInMillis
    }
}