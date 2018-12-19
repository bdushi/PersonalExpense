package al.bruno.personal.expense.model

import androidx.room.ColumnInfo

import androidx.room.DatabaseView
import androidx.room.Ignore
import java.text.DecimalFormat

@DatabaseView("SELECT " +
        "e._date AS _date, " +
        "TOTAL(e._amount) - TOTAL(e._income) AS _remain, " +
        "e._income AS _income, " +
        "TOTAL(e._amount) AS _amount, " +
        "e._income - TOTAL(e._amount) AS _balance " +
        "FROM expense AS e", viewName = "budget_details")
class BudgetDetails() {
    @ColumnInfo(name = "_remain")
    var budget:Float = 0.toFloat()
    @ColumnInfo(name = "_income")
    var incomes:Float = 0.toFloat()
    @ColumnInfo(name = "_amount")
    var expense:Float = 0.toFloat()
    @ColumnInfo(name = "_balance")
    var balance:Float = 0.toFloat()

    @Ignore
    val decimalFormat = DecimalFormat("LEK ###,###.###")
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
