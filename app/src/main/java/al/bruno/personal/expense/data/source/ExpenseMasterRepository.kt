package al.bruno.personal.expense.data.source

import al.bruno.personal.expense.model.ExpenseMaster
import io.reactivex.Single

class ExpenseMasterRepository(private var expenseDataSource: ExpenseMasterDataSource) : ExpenseMasterDataSource {
    companion object {
        private var INSTANCE: ExpenseMasterDataSource? = null
        fun getInstance (expenseDataSource: ExpenseMasterDataSource) : ExpenseMasterDataSource? {
            if(INSTANCE == null)
                INSTANCE = ExpenseMasterRepository(expenseDataSource)
            return INSTANCE
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
    override fun expenseMaster(month: String, year: String): Single<List<ExpenseMaster>> {
        return expenseDataSource.expenseMaster(month, year)
    }
}