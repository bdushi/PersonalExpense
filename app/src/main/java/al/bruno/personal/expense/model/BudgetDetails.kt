package al.bruno.personal.expense.model

import androidx.room.ColumnInfo

import androidx.room.DatabaseView
import androidx.room.Ignore
import java.text.DecimalFormat

@DatabaseView("SELECT " +
        "i._date AS _date, " +
        "i._incomes AS _budget, " +
        "i._incomes AS _incomes, " +
        "TOTAL(e._amount) AS _amount, " +
        "i._incomes - TOTAL(e._amount) AS _balance " +
        "FROM incomes AS i " +
        "LEFT JOIN expense AS e ON i._id = e._id_budget GROUP BY e._id_budget", viewName = "budget_details")
class BudgetDetails() {
    @ColumnInfo(name = "_budget")
    var budget:Float = 0.toFloat()
    @ColumnInfo(name = "_incomes")
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
