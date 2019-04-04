package al.bruno.personal.expense.util

import com.github.mikephil.charting.utils.ColorTemplate
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat
import java.text.DecimalFormat
import java.util.*

object Utilities {

    fun format(value: Double, int: Int) : String {
        return when (int) {
            0 -> DecimalFormat("###,###.### LEK").format(value)
            1 -> DecimalFormat("###").format(value)
            2 -> DecimalFormat("###,###.###").format(value)
            else -> DecimalFormat("###").format(value)
        }
    }

    fun dateFormat(date: DateTime): String {
        return DateTimeFormat.forPattern("dd-MMM-yyyy").print(date)
    }
    fun dayFormat(date: DateTime): String {
        return DateTimeFormat.forPattern("dd/MM EEE").print(date)
    }

    fun date(date: DateTime): String {
        return DateTimeFormat.forPattern("dd").print(date)
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

    fun month(date: Long): String {
        return DateTimeFormat.forPattern("MMMM").print(date)
    }

    fun month(calendar: Calendar): String {
        return DateTimeFormat.forPattern("MMM").print(calendar.timeInMillis)
    }

    internal fun colors(): ArrayList<Int>? {
        val colors = ArrayList<Int>()
        colors.add(ColorTemplate.getHoloBlue())
        for (c in ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c)

        for (c in ColorTemplate.JOYFUL_COLORS)
            colors.add(c)

        for (c in ColorTemplate.COLORFUL_COLORS)
            colors.add(c)

        for (c in ColorTemplate.LIBERTY_COLORS)
            colors.add(c)

        for (c in ColorTemplate.PASTEL_COLORS)
            colors.add(c)
        return colors
    }

    fun randomColors(): Int? {
        val colors = ArrayList<Int>()
        colors.add(ColorTemplate.getHoloBlue())
        for (c in ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c)

        for (c in ColorTemplate.JOYFUL_COLORS)
            colors.add(c)

        for (c in ColorTemplate.COLORFUL_COLORS)
            colors.add(c)

        for (c in ColorTemplate.LIBERTY_COLORS)
            colors.add(c)

        for (c in ColorTemplate.PASTEL_COLORS)
            colors.add(c)
        return colors[Random().nextInt(colors.size)]
    }
}
