package al.bruno.financaime.data.source

import al.bruno.financaime.model.BudgetMaster
import io.reactivex.Single

class BudgetMasterRepository(budgetMasterRepository: BudgetMasterDataSource) : BudgetMasterDataSource {
    var budgetMasterRepository: BudgetMasterDataSource? = null
    init {
        this.budgetMasterRepository = budgetMasterRepository
    }

    companion object {
        private var INSTANCE: BudgetMasterDataSource? = null
        fun getInstance (budgetMasterRepository: BudgetMasterDataSource): BudgetMasterDataSource? {
           if(INSTANCE == null)
               INSTANCE = BudgetMasterRepository(budgetMasterRepository)
            return INSTANCE
        }
    }
    override fun budget(month: String): Single<BudgetMaster> {
        return INSTANCE!!.budget(month)
    }
}