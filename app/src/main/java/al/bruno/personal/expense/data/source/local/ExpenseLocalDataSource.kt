package al.bruno.personal.expense.data.source.local

import al.bruno.personal.expense.data.source.ExpenseDataSource
import al.bruno.personal.expense.data.source.local.dao.ExpenseDao
import al.bruno.personal.expense.model.Expense
import androidx.lifecycle.LiveData
import io.reactivex.Single
import org.joda.time.DateTime

class ExpenseLocalDataSource(private val expenseDao: ExpenseDao) : ExpenseDataSource {
    companion object {
        private var INSTANCE : ExpenseDataSource? = null
        fun getInstance(expenseDao: ExpenseDao) : ExpenseDataSource? {
            if(INSTANCE == null)
                INSTANCE = ExpenseLocalDataSource(expenseDao)
            return INSTANCE
        }
    }
    override fun insert(expense: Expense): Single<Long> {
        return expenseDao.insert(expense)
    }

    override fun expense(id: Long): LiveData<Expense> {
        return expenseDao.expense(id)
    }

    override fun expenses(month: String): LiveData<List<Expense>> {
        return expenseDao.expenses(month)
    }

    override fun expenses(month: String, year: String): LiveData<List<Expense>> {
        return expenseDao.expenses(month, year)
    }

    override fun date(): Single<Array<DateTime>> {
        return expenseDao.date()
    }
    override fun expenses(date: DateTime): Single<List<Expense>> {
        return expenseDao.expenses(date)
    }

    override fun total(date: DateTime): Single<String> {
        return expenseDao.total(date)
    }
}