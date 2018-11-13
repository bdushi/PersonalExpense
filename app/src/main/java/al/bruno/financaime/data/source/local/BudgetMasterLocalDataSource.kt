package al.bruno.financaime.data.source.local

import al.bruno.financaime.data.source.BudgetMasterDataSource
import al.bruno.financaime.model.BudgetMaster
import android.content.Context
import io.reactivex.Single

class BudgetMasterLocalDataSource(context: Context) : BudgetMasterDataSource {
    private val DATABASE_INSTANCE: AppDatabase

    init {
        DATABASE_INSTANCE = AppDatabase.getInstance(context)
    }

    companion object {
        var INSTANCE: BudgetMasterDataSource? = null
        fun INSTANCE (context: Context) : BudgetMasterDataSource? {
            if(INSTANCE == null)
                INSTANCE = BudgetMasterLocalDataSource(context)
            return INSTANCE
        }

    }

    override fun budget(month: String): Single<BudgetMaster> {
        return DATABASE_INSTANCE.budgetMasterDao().budget(month)
    }
}