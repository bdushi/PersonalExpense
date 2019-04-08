package al.bruno.personal.expense.data.source

import al.bruno.personal.expense.data.source.local.dao.ExpenseDetailsDao
import al.bruno.personal.expense.model.ExpenseDetails
import androidx.lifecycle.LiveData
import io.reactivex.Flowable
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ExpenseDetailsRepository @Inject constructor(private var expenseDetailsDataSource: ExpenseDetailsDataSource) : ExpenseDetailsDataSource {
    override fun budgetDetails(month: String, year: String): LiveData<ExpenseDetails> {
        return expenseDetailsDataSource.budgetDetails(month, year)
    }

}