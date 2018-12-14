package al.bruno.personal.expense.data.source

import al.bruno.personal.expense.model.Expense
import androidx.lifecycle.LiveData
import io.reactivex.Single
import org.joda.time.DateTime

class ExpenseRepository(private var expenseDataSource: ExpenseDataSource) : ExpenseDataSource {
    companion object {
        private var INSTANCE: ExpenseDataSource? = null
        fun getInstance (expenseDataSource: ExpenseDataSource) : ExpenseDataSource? {
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

    override fun date(): Single<List<DateTime>> {
        return expenseDataSource.date()
    }
    override fun expenses(date: DateTime): Single<List<Expense>> {
        return expenseDataSource.expenses(date)
    }

    override fun total(date: DateTime): Single<String> {
        return expenseDataSource.total(date)
    }
}