package al.bruno.personal.expense.model

import androidx.room.ColumnInfo
import androidx.room.DatabaseView
import org.joda.time.DateTime

@DatabaseView("select _amount, _date, _type from (" +
        "select TOTAL(_amount) AS _amount, _date, _type from expense where _type = 'expenses' GROUP BY _date " +
        "union all " +
        "select TOTAL(_amount) AS _amount, _date, _type from expense where _type = 'incomes'  GROUP BY _date " +
        "union all " +
        "select TOTAL(_amount) AS _amount, _date, 'balance' from (" +
        "select _amount, _date, _type from expense where _type = 'expenses' " +
        "union all " +
        "select -_amount, _date, _type from expense where _type = 'incomes') GROUP BY _date)", viewName = "expense_chart")
class ExpenseChart {
    @ColumnInfo(name = "_type")
    var type:String = ""

    @ColumnInfo(name = "_amount")
    var amount:Float = 0.toFloat()

    @ColumnInfo(name = "_date")
    var date: DateTime? = null
}