package al.bruno.financaime.dependency.injection

import al.bruno.financaime.data.source.BudgetMasterDataSource
import al.bruno.financaime.data.source.BudgetMasterRepository
import al.bruno.financaime.data.source.local.BudgetMasterLocalDataSource
import android.content.Context

object BudgetMasterInjection {
    fun providerBudgetMasterInjection(context: Context): BudgetMasterDataSource? {
        return BudgetMasterRepository.getInstance(BudgetMasterLocalDataSource.INSTANCE(context)!!)
    }
}