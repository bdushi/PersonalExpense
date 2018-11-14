package al.bruno.financaime.data.source.local

import al.bruno.financaime.data.source.BudgetMasterDataSource
import al.bruno.financaime.model.BudgetMaster
import android.content.Context
import androidx.lifecycle.LiveData

class BudgetMasterLocalDataSource(context: Context) : BudgetMasterDataSource {
    private val appDatabase: AppDatabase = AppDatabase.getInstance(context)
    companion object {
        var INSTANCE: BudgetMasterDataSource? = null
        fun newInstance (context: Context) : BudgetMasterDataSource? {
            if(INSTANCE == null)
                INSTANCE = BudgetMasterLocalDataSource(context)
            return INSTANCE
        }
    }

    override fun budget(month: String): LiveData<BudgetMaster> {
        return appDatabase.budgetMasterDao().budget(month)
    }
}