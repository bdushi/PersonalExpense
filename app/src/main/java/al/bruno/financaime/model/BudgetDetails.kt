package al.bruno.financaime.model

import android.database.Cursor

import java.util.Date

import al.bruno.financaime.model.BudgetDetails.BudgetMasterTable.BALANCE
import al.bruno.financaime.model.BudgetDetails.BudgetMasterTable.BUDGET
import al.bruno.financaime.model.BudgetDetails.BudgetMasterTable.EXPENSE
import al.bruno.financaime.model.BudgetDetails.BudgetMasterTable.INCOMES
import androidx.room.DatabaseView

@DatabaseView("SELECT b._budget AS _budget, b._incomes AS _incomes, SUM(e._expense) AS _expense, b._incomes - SUM(e._expense) AS _balance " +
        "FROM budget AS b " +
        "LEFT JOIN expense AS e ON b._id = e._id_budget " +
        "GROUP BY e._id_budget")
class BudgetDetails {
    var budget = 0.0
    var incomes = 0.0
    var expense = 0.0
    var balance = 0.0
    private var date: Date? = null

    constructor(cursor: Cursor) {
        cursor.moveToFirst()
        while (!cursor.isAfterLast) {
            budget = cursor.getDouble(cursor.getColumnIndex(BUDGET))
            incomes = cursor.getDouble(cursor.getColumnIndex(INCOMES))
            expense = cursor.getDouble(cursor.getColumnIndex(EXPENSE))
            balance = cursor.getDouble(cursor.getColumnIndex(BALANCE))
            cursor.moveToNext()
        }
        cursor.close()
    }

    constructor() {

    }

    fun getDate(): Date? {
        return date
    }

    fun setDate(date: Date): BudgetDetails {
        this.date = date
        return this
    }

    object BudgetMasterTable {
        val BUDGET = "_budget"
        val INCOMES = "_incomes"
        val EXPENSE = "_expense"
        val BALANCE = "_balance"
    }
}
