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
            else -> return DecimalFormat("###").format(value)
        }
    }

    fun dateFormat(date: DateTime): String {
        return DateTimeFormat.forPattern("dd-MMM-yyyy").print(date)
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

    fun monthFormat(date: Long): String {
        return DateTimeFormat.forPattern("MMMM yyyy").print(date)
    }

    fun month(date: DateTime): String {
        return DateTimeFormat.forPattern("MMMM").print(date)
    }

    fun date(date: Int): String {
        when (date) {
            1 -> return "01"
            2 -> return "02"
            3 -> return "03"
            4 -> return "04"
            5 -> return "05"
            6 -> return "06"
            7 -> return "07"
            8 -> return "08"
            9 -> return "09"
            else -> return date.toString()
        }
    }
}
