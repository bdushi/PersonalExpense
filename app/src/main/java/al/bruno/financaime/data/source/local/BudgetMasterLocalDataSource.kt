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
        var INSTANCE: BudgetMasterLocalDataSource? = null
        fun INSTANCE(context: Context): BudgetMasterLocalDataSource {
            if (INSTANCE == null)
                INSTANCE = BudgetMasterLocalDataSource(context)
            return INSTANCE as BudgetMasterLocalDataSource
        }
    }

    override fun budget(month: String): Single<List<BudgetMaster>> {
        return INSTANCE.budgetMasterDao().budget(month)
    }
}