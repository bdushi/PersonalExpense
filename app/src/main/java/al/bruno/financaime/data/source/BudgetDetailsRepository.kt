package al.bruno.financaime.data.source

import al.bruno.financaime.model.BudgetDetails
import io.reactivex.Single

class BudgetDetailsRepository(budgetDetailsDataSource: BudgetDetailsDataSource) : BudgetDetailsDataSource {
    private var budgetDetailsDataSource: BudgetDetailsDataSource? = null

    companion object {
        private var INSTANCE: BudgetDetailsDataSource? = null
        fun getInstance (budgetMasterDataSource: BudgetDetailsDataSource): BudgetDetailsDataSource? {
            if(INSTANCE == null)
                INSTANCE = BudgetDetailsRepository(budgetMasterDataSource)
            return INSTANCE
        }
        fun destroyInstance() {
            INSTANCE = null
        }
    }

    override fun budgetDetails(month: String, year: String): Single<BudgetDetails> {
        return budgetDetailsDataSource!!.budgetDetails(month, year)
    }

}