package al.bruno.personal.expense.data.source.local

import al.bruno.personal.expense.data.source.BudgetMasterDataSource
import al.bruno.personal.expense.model.BudgetMaster
import android.content.Context
import androidx.lifecycle.LiveData

class BudgetMasterLocalDataSource(context: Context) : BudgetMasterDataSource {
    private val appDatabase: AppDatabase = AppDatabase.getInstance(context)
    companion object {
        private var INSTANCE: BudgetMasterDataSource? = null
        fun INSTANCE (context: Context) : BudgetMasterDataSource? {
            if(INSTANCE == null)
                INSTANCE = BudgetMasterLocalDataSource(context)
            return INSTANCE
        }
    }

    override fun budget(month: String): LiveData<BudgetMaster> {
        return appDatabase.budgetMasterDao().budget(month)
    }
}