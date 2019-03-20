package al.bruno.personal.expense.view.model

import al.bruno.personal.expense.data.source.ExpenseDataSource
import al.bruno.personal.expense.data.source.ExpenseRepository
import al.bruno.personal.expense.model.Expense
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import io.reactivex.Single
import org.joda.time.DateTime
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ExpenseViewModel constructor(@Inject private val expenseRepository: ExpenseRepository) : ViewModel(), ExpenseDataSource {
    override fun insert(expense: Expense): Single<Long> {
        return expenseRepository.insert(expense)
    }

    override fun expense(id: Long): LiveData<Expense> {
        return expenseRepository.expense(id)
    }

    override fun expenses(month: String, year: String): Single<List<Expense>> {
        return expenseRepository.expenses(month, year)
    }

    override fun statistics(month: String, year: String): Single<List<Expense>> {
        return expenseRepository.statistics(month, year)
    }

    override fun date(): Single<Array<DateTime>> {
        return expenseRepository.date()
    }

    override fun expenses(date: DateTime): Single<List<Expense>> {
        return expenseRepository.expenses(date)
    }

    override fun total(date: DateTime): Single<String> {
        return expenseRepository.total(date)
    }
}