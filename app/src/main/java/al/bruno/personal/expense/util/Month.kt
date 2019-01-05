package al.bruno.personal.expense.util

import java.text.SimpleDateFormat
import java.util.*

class Month (private val calendar: Calendar) {
    fun month(): String {
        return SimpleDateFormat("MMM", Locale.getDefault()).format((calendar.timeInMillis))
    }

    fun calendar(): Calendar {
        return calendar
    }

    override fun toString(): String {
        return SimpleDateFormat("MMM", Locale.getDefault()).format((calendar.timeInMillis))
    }
}