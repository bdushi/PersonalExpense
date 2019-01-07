package al.bruno.personal.expense.util

import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat
import java.text.DecimalFormat
import java.util.Calendar
import java.util.Date

object Utilities {

    fun format(value: Double, int: Int) : String {
        when (int) {
            0 -> return DecimalFormat("###,###.### LEK").format(value)
            1 -> return DecimalFormat("###").format(value)
            2 -> return DecimalFormat("###,###.###").format(value)
            else -> return DecimalFormat("###").format(value)
        }
    }

    fun dateFormat(date: DateTime): String {
        return DateTimeFormat.forPattern("dd-MMM-yyyy").print(date)
    }
    fun expenseDate(date: DateTime): String {
        return DateTimeFormat.forPattern("dd/M/yyyy").print(date)
    }

    fun month(): Int {
        val calendar = Calendar.getInstance()
        return calendar.get(Calendar.MONTH)
    }

    fun month(date: Date): Int {
        val calendar = Calendar.getInstance()
        calendar.time = date
        return calendar.get(Calendar.MONTH)
    }

    fun month(month: Int): String {
        return arrayOf("01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12")[month]
    }

    fun year(date: Long): String {
        return DateTimeFormat.forPattern("yyyy").print(date)
    }

    fun monthFormat(date: Long): String {
        return DateTimeFormat.forPattern("MMMM yyyy").print(date)
    }

    fun month(date: DateTime): String {
        return DateTimeFormat.forPattern("MMMM").print(date)
    }

    fun month(calendar: Calendar): String {
        return DateTimeFormat.forPattern("MMM").print(calendar.timeInMillis)
    }
}
