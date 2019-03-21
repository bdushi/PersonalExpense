package al.bruno.personal.expense.data.source.local

import al.bruno.personal.expense.data.source.ExpenseDetailsDataSource
import al.bruno.personal.expense.data.source.local.dao.ExpenseDetailsDao
import al.bruno.personal.expense.model.ExpenseDetails
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ExpenseDetailsLocalDataSource @Inject constructor(private val expenseDetailsDao: ExpenseDetailsDao) : ExpenseDetailsDataSource {
    override fun budgetDetails(month: String, year: String): Single<ExpenseDetails> {
        return expenseDetailsDao.budgetDetails(month, year)
    }
}