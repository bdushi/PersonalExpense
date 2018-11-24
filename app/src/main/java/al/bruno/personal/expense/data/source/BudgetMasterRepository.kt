package al.bruno.personal.expense.data.source

import al.bruno.personal.expense.model.BudgetMaster
import androidx.lifecycle.LiveData
class BudgetMasterRepository(budgetMasterDataSource: BudgetMasterDataSource) : BudgetMasterDataSource {
    private var budgetMasterDataSource: BudgetMasterDataSource? = null
    init {
        this.budgetMasterDataSource = budgetMasterDataSource
    }

    companion object {
        private var INSTANCE: BudgetMasterDataSource? = null
        fun getInstance (budgetMasterDataSource: BudgetMasterDataSource): BudgetMasterDataSource? {
           if(INSTANCE == null)
               INSTANCE = BudgetMasterRepository(budgetMasterDataSource)
            return INSTANCE
        }
        fun destroyInstance() {
            INSTANCE = null
        }
    }
    override fun budget(month: String): LiveData<BudgetMaster> {
        return budgetMasterDataSource!!.budget(month)
    }
}