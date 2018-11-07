package al.bruno.financaime.model

import androidx.room.ColumnInfo

import androidx.room.DatabaseView
import androidx.room.Relation

@DatabaseView("SELECT " +
        "b._budget AS _budget, " +
        "b._incomes AS _incomes, " +
        "TOTAL(e._amount) AS _amount, " +
        "b._incomes - SUM(e._amount) AS _balance " +
        "FROM budget AS b " +
        "LEFT JOIN expense AS e ON b._id = e._id_budget", viewName = "budget_details")
class BudgetDetails() {
    @ColumnInfo(name = "_budget")
    var budget:Double = 0.0
    @ColumnInfo(name = "_incomes")
    var incomes:Double = 0.0
    @ColumnInfo(name = "_amount")
    var expense:Double = 0.0
    @ColumnInfo(name = "_balance")
    var balance:Double = 0.0

    /*constructor(budget: Double, incomes: Double, expense: Double, balance: Double) {
        this.budget = budget;
        this.incomes = incomes;
        this.expense = expense;
        this.balance = balance;
    }
    constructor() : this(0.0, 0.0, 0.0,0.0)*/
}
