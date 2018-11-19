package al.bruno.financaime.dependency.injection

import al.bruno.financaime.data.source.BudgetMasterDataSource
import al.bruno.financaime.data.source.BudgetMasterRepository
import al.bruno.financaime.data.source.BudgetMasterRepository.Companion.getInstance
import al.bruno.financaime.data.source.local.BudgetMasterLocalDataSource
import al.bruno.financaime.data.source.local.BudgetMasterLocalDataSource.Companion.newInstance
import android.content.Context

object BudgetMasterInjection {
    fun providerBudgetMasterInjection(context: Context): BudgetMasterDataSource? {
        return getInstance(newInstance(context)!!)
    }
}