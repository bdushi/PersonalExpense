package al.bruno.personal.expense.util

import java.util.*

class MonthNavigation (private val calendar: Calendar){
    var month: Array<Month>? = null
    init {
        month = Array(12) {
            calendar.set(Calendar.MONTH, it)
            Month(calendar = calendar)}
    }
}