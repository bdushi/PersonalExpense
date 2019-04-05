package al.bruno.personal.expense.data.source

import al.bruno.personal.expense.model.Expense
import androidx.lifecycle.LiveData
import io.reactivex.Completable
import io.reactivex.Single
import org.joda.time.DateTime
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ExpenseRepository @Inject constructor(private var expenseDataSource: ExpenseDataSource) : ExpenseDataSource {
    override fun insert(expenses: List<Expense>): Completable {
        return expenseDataSource.insert(expenses)
    }

    override fun insert(expense: Expense): Single<Long> {
        return expenseDataSource.insert(expense)
    }

    override fun expense(id: Long): LiveData<Expense> {
        return expenseDataSource.expense(id)
    }

    override fun expenses(month: String, year: String): Single<List<Expense>> {
        return expenseDataSource.expenses(month, year)
    }

    override fun statistics(month: String, year: String): Single<List<Expense>> {
        return expenseDataSource.statistics(month, year)
    }

    override fun date(): Single<Array<DateTime>> {
        return expenseDataSource.date()
    }
    override fun expenses(date: DateTime): Single<List<Expense>> {
        return expenseDataSource.expenses(date)
    }

    override fun total(date: DateTime): Single<String> {
        return expenseDataSource.total(date)
    }
}