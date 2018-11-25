package al.bruno.personal.expense.data.source

import al.bruno.personal.expense.model.ExpenseDetails
import io.reactivex.Single

class ExpenseDetailsRepository(private val expenseDetailsDataSource: ExpenseDetailsDataSource): ExpenseDetailsDataSource {
    companion object {
        private var expenseDetailsDataSource: ExpenseDetailsDataSource? = null
        fun getInstance(mExpenseDetailsDataSource: ExpenseDetailsDataSource):ExpenseDetailsDataSource? {
            if(expenseDetailsDataSource == null) {
                expenseDetailsDataSource = ExpenseDetailsRepository(mExpenseDetailsDataSource)
            }
            return expenseDetailsDataSource
        }
        fun destroyInstance() {
            expenseDetailsDataSource = null
        }
    }
    override fun expense(): Single<ExpenseDetails> {
        return expenseDetailsDataSource.expense()
    }

}