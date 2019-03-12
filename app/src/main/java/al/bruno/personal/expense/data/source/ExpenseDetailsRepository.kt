package al.bruno.personal.expense.data.source

import al.bruno.personal.expense.model.ExpenseDetails
import io.reactivex.Single

class ExpenseDetailsRepository(expenseDetailsDataSource: ExpenseDetailsDataSource) : ExpenseDetailsDataSource {
    private var expenseDetailsDataSource: ExpenseDetailsDataSource? = null
    init {
        this.expenseDetailsDataSource = expenseDetailsDataSource
    }
    companion object {
        private var INSTANCE: ExpenseDetailsDataSource? = null
        fun getInstance (expenseMasterDataSource: ExpenseDetailsDataSource): ExpenseDetailsDataSource? {
            if(INSTANCE == null)
                INSTANCE = ExpenseDetailsRepository(expenseMasterDataSource)
            return INSTANCE
        }
        fun destroyInstance() {
            INSTANCE = null
        }
    }

    override fun budgetDetails(month: String, year: String): Single<ExpenseDetails> {
        return expenseDetailsDataSource!!.budgetDetails(month, year)
    }

}