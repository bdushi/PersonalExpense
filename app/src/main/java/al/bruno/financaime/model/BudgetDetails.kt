package al.bruno.financaime.model

import al.bruno.financaime.util.Utilities
import androidx.room.ColumnInfo

import androidx.room.DatabaseView
import androidx.room.Ignore
import com.amitshekhar.utils.Utils
import java.text.DecimalFormat

@DatabaseView("SELECT " +
        "b._date AS _date, " +
        "b._budget AS _budget, " +
        "b._incomes AS _incomes, " +
        "TOTAL(e._amount) AS _amount, " +
        "b._incomes - TOTAL(e._amount) AS _balance " +
        "FROM budget AS b " +
        "LEFT JOIN expense AS e ON b._id = e._id_budget GROUP BY e._id_budget", viewName = "budget_details")
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
