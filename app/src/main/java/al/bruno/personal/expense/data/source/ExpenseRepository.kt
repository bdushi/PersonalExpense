package al.bruno.personal.expense.data.source

import al.bruno.personal.expense.model.Expense
import androidx.lifecycle.LiveData
import io.reactivex.Single
import java.util.*

class ExpenseRepository(expenseDataSource: ExpenseDataSource) : ExpenseDataSource {
    private var expenseDataSource: ExpenseDataSource = expenseDataSource

    companion object {
        private var INSTANCE: ExpenseDataSource? = null
        fun newInstance (expenseDataSource: ExpenseDataSource) : ExpenseDataSource? {
            if(INSTANCE == null)
                INSTANCE = ExpenseRepository(expenseDataSource)
            return INSTANCE
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
    override fun insert(expense: Expense): Single<Long> {
        return expenseDataSource.insert(expense)
    }

    override fun expense(id: Long): LiveData<Expense> {
        return expenseDataSource.expense(id)
    }

    override fun expenses(month: String): LiveData<List<Expense>> {
        return expenseDataSource.expenses(month)
    }

    override fun expenses(month: String, year: String): LiveData<List<Expense>> {
        return expenseDataSource.expenses(month, year)
    }

    override fun date(): Single<List<Date>> {
        return expenseDataSource.date()
    }

}