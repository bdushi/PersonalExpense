package al.bruno.personal.expense.model

import androidx.room.ColumnInfo

import androidx.room.DatabaseView
import androidx.room.Ignore
import java.text.DecimalFormat

@DatabaseView("SELECT " +
        "e._date AS _date, " +
        "TOTAL(CASE WHEN e._type = 'incomes' THEN  e._amount ELSE 0 END) AS _income, " +
        "TOTAL(CASE WHEN e._type = 'expenses' THEN  e._amount ELSE 0 END) AS _expenses, " +
        "TOTAL(CASE WHEN e._type = 'incomes' THEN  e._amount ELSE 0 END) - TOTAL(CASE WHEN e._type = 'expenses' THEN  e._amount ELSE 0 END) AS _remain, " +
        "TOTAL(CASE WHEN e._type = 'incomes' THEN  e._amount ELSE 0 END) - TOTAL(CASE WHEN e._type = 'expenses' THEN  e._amount ELSE 0 END) AS _balance " +
        "FROM expense AS e GROUP BY strftime('%m%Y', datetime(_date/1000, 'unixepoch'))", viewName = "expense_details")
class ExpenseDetails {
    @ColumnInfo(name = "_remain")
    var budget:Float = 0.toFloat()
    @ColumnInfo(name = "_income")
    var incomes:Float = 0.toFloat()
    @ColumnInfo(name = "_expenses")
    var expense:Float = 0.toFloat()
    @ColumnInfo(name = "_balance")
    var balance:Float = 0.toFloat()

    @Ignore
    val decimalFormat = DecimalFormat("###,###.### LEK")
    @Ignore
    var budgetStr : String? = ""
        get() {
            return decimalFormat.format(budget)
        }
    @Ignore
    var incomesStr : String? = ""
        get() {
            return decimalFormat.format(incomes)
        }
    @Ignore
    var expenseStr : String? = ""
        get() {
            return decimalFormat.format(expense)
        }
    @Ignore
    var balanceStr : String? = ""
    get() {
        return decimalFormat.format(balance)
    }
}
