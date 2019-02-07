package al.bruno.personal.expense.data.source.local

import al.bruno.personal.expense.data.source.BudgetMasterDataSource
import al.bruno.personal.expense.data.source.local.dao.BudgetMasterDao
import al.bruno.personal.expense.model.BudgetMaster
import androidx.lifecycle.LiveData

class BudgetMasterLocalDataSource(private val budgetMasterDao: BudgetMasterDao) : BudgetMasterDataSource {
    companion object {
        private var INSTANCE: BudgetMasterDataSource? = null
        fun getInstance (budgetMasterDao: BudgetMasterDao) : BudgetMasterDataSource? {
            if(INSTANCE == null)
                INSTANCE = BudgetMasterLocalDataSource(budgetMasterDao)
            return INSTANCE
        }
    }

    override fun budget(month: String): LiveData<BudgetMaster> {
        return budgetMasterDao.budget(month)
    }
}