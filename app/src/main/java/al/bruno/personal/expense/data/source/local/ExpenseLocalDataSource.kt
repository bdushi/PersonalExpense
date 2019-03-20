package al.bruno.personal.expense.data.source.local

import al.bruno.personal.expense.data.source.ExpenseDataSource
import al.bruno.personal.expense.data.source.local.dao.ExpenseDao
import al.bruno.personal.expense.model.Expense
import androidx.lifecycle.LiveData
import io.reactivex.Single
import org.joda.time.DateTime
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ExpenseLocalDataSource constructor(@Inject private val expenseDao: ExpenseDao) : ExpenseDataSource {
    override fun insert(expense: Expense): Single<Long> {
        return expenseDao.insert(expense)
    }

    override fun expense(id: Long): LiveData<Expense> {
        return expenseDao.expense(id)
    }

    override fun expenses(month: String, year: String): Single<List<Expense>> {
        return expenseDao.expenses(month, year)
    }

    override fun statistics(month: String, year: String): Single<List<Expense>> {
        return expenseDao.statistics(month, year)
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