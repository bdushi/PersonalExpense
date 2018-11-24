package al.bruno.personal.expense.util

import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.DayViewFacade
import com.prolificinteractive.materialcalendarview.spans.DotSpan

import org.threeten.bp.DateTimeUtils
import org.threeten.bp.ZoneId

import java.util.Date
import java.util.HashSet

class EventDecorator(private val color: Int, /*Collection<CalendarDay> dates*/dateList: List<Date>) : DayViewDecorator {
    private val dates: HashSet<CalendarDay>

    init {
        this.dates = HashSet()
        for (date in dateList) {
            dates.add(CalendarDay.from(DateTimeUtils.toInstant(date).atZone(ZoneId.systemDefault()).toLocalDate()))
        }
    }

    override fun shouldDecorate(day: CalendarDay): Boolean {
        return dates.contains(day)
    }

    override fun decorate(view: DayViewFacade) {
        view.addSpan(DotSpan(5f, color))
    }
}
