package al.bruno.personal.expense.dependency.injection

import al.bruno.personal.expense.data.source.BudgetMasterDataSource
import al.bruno.personal.expense.data.source.BudgetMasterRepository.Companion.getInstance
import al.bruno.personal.expense.data.source.local.BudgetMasterLocalDataSource.Companion.newInstance
import android.content.Context

object BudgetMasterInjection {
    fun providerBudgetMasterInjection(context: Context): BudgetMasterDataSource? {
        return getInstance(newInstance(context)!!)
    }
}