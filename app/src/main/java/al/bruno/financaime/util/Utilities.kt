package al.bruno.financaime.util

import com.prolificinteractive.materialcalendarview.CalendarDay

import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

object Utilities {

    fun format(value: Double, int: Int) : String {
        return when (int) {
            0 ->{
                DecimalFormat("LEK ###,###.###").format(value)
            }
            1 ->{
                DecimalFormat("###").format(value)
            }
            else -> {
                ""
            }
        }
    }

    fun dateFormat(date: Date): String {
        val dateFormat = SimpleDateFormat("dd-MMM-yyyy")
        return dateFormat.format(date.time)
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
        val monthStr = arrayOf("01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12")
        return monthStr[month]
    }

    fun monthIncrementAndDecrement(calendar: Calendar, month: Int): Calendar {
        calendar.add(Calendar.MONTH, month)
        return calendar
    }

    fun format(calendar: Calendar): String {
        return String.format("%s %s %s", calendar.get(Calendar.DATE), getMonth(calendar.get(Calendar.MONTH)), calendar.get(Calendar.YEAR))
    }

    fun monthFormat(calendar: Calendar): String {
        return String.format("%s %s", getMonth(calendar.get(Calendar.MONTH)), calendar.get(Calendar.YEAR))
    }

    fun date(): Date {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        return calendar.time
    }

    fun calendar(): Calendar {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        return calendar
    }

    fun calendar(date: CalendarDay): Calendar {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.DATE, date.day)
        calendar.set(Calendar.MONTH, date.month)
        calendar.set(Calendar.YEAR, date.year)
        calendar.set(Calendar.HOUR, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        return calendar
    }

    fun date(calendar: Calendar): Date {
        calendar.set(Calendar.HOUR, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        return calendar.time
    }

    fun fromTimestamp(value: Long?): Date? {
        return if (value == null) null else Date(value)
    }

    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }

    fun getMonth(month: Int): String {
        val monthStr = arrayOf("Janare", "Shkurte", "Marse", "Prill", "Maj", "Qershor", "Korrik", "Gusht", "Shtatore", "Tetor", "Nentore", "Dhjetore")
        return monthStr[month]
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
