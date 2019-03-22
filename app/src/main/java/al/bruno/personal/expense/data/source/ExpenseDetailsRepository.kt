package al.bruno.personal.expense.data.source

import al.bruno.personal.expense.data.source.local.dao.ExpenseDetailsDao
import al.bruno.personal.expense.model.ExpenseDetails
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ExpenseDetailsRepository @Inject constructor(private var expenseDetailsDataSource: ExpenseDetailsDao) : ExpenseDetailsDataSource {
    override fun budgetDetails(month: String, year: String): Single<ExpenseDetails> {
        return expenseDetailsDataSource.budgetDetails(month, year)
    }

}