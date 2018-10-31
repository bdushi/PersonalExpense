package al.bruno.financaime.model

import al.bruno.financaime.model.Expense.ExpenseTable.Companion.DATE
import al.bruno.financaime.model.Expense.ExpenseTable.Companion.EXPENSE
import al.bruno.financaime.model.Expense.ExpenseTable.Companion.EXPENSE_NAME
import android.database.Cursor

import java.util.ArrayList

import al.bruno.financaime.util.Utilities

import al.bruno.financaime.model.ExpenseMaster.ExpenseMasterTable.TOTAL
import al.bruno.financaime.util.Utilities.format

//ignore
class ExpenseMaster {
    var total = "0"
    private var id: Long = 1
    private var expenses: ArrayList<Expense> = ArrayList()

    constructor(expenses: ArrayList<Expense>) {
        this.expenses = expenses
    }

    constructor() {}
    constructor(cursor: Cursor) {
        cursor.moveToFirst()
        while (!cursor.isAfterLast) {
            total = format(cursor.getDouble(cursor.getColumnIndex(TOTAL)))
            while (!cursor.isAfterLast) {
                expenses.add(Expense(
                        cursor.getString(cursor.getColumnIndex(EXPENSE_NAME)),
                        cursor.getDouble(cursor.getColumnIndex(EXPENSE)),
                        Utilities.fromTimestamp(cursor.getLong(cursor.getColumnIndex(DATE)))!!,
                        id++)
                )
                cursor.moveToNext()
            }
            setExpenses(expenses)
            cursor.moveToNext()
        }
        cursor.close()
    }

    fun getExpenses(): List<Expense> {
        return expenses
    }

    fun setExpenses(expenses: ArrayList<Expense>) {
        this.expenses = expenses
    }

    object ExpenseMasterTable {
        val TOTAL = "_total"
    }
}
