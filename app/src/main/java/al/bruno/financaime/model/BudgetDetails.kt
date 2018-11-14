package al.bruno.financaime.model

import androidx.room.ColumnInfo

import androidx.room.DatabaseView

@DatabaseView("SELECT " +
        "b._date AS _date, " +
        "b._budget AS _budget, " +
        "b._incomes AS _incomes, " +
        "TOTAL(e._amount) AS _amount, " +
        "b._incomes - SUM(e._amount) AS _balance " +
        "FROM budget AS b " +
        "LEFT JOIN expense AS e ON b._id = e._id_budget", viewName = "budget_details")
class BudgetDetails() {
    @ColumnInfo(name = "_budget")
    var budget:Float = 0.toFloat()
    @ColumnInfo(name = "_incomes")
    var incomes:Float = 0.toFloat()
    @ColumnInfo(name = "_amount")
    var expense:Float = 0.toFloat()
    @ColumnInfo(name = "_balance")
    var balance:Float = 0.toFloat()

    /*val decimalFormat = DecimalFormat("LEK ###,###.###")
    var budgetStr : String? = ""
        get() {
            return decimalFormat.format(budget)
        }
    var incomesStr : String? = ""
        get() {
            return decimalFormat.format(incomes)
        }
    var expenseStr : String? = ""
        get() {
            return decimalFormat.format(expense)
        }
    var balanceStr : String? = ""
    get() {
        return decimalFormat.format(balance)
    }*/
}
