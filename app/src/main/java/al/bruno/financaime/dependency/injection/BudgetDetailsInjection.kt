package al.bruno.financaime.dependency.injection

import al.bruno.financaime.data.source.BudgetDetailsDataSource
import al.bruno.financaime.data.source.BudgetDetailsRepository
import al.bruno.financaime.data.source.local.BudgetDetailsLocalDataSource
import android.content.Context
import androidx.annotation.NonNull

object BudgetDetailsInjection {
    fun provideBudgetDetailsInjection(@NonNull context: Context): BudgetDetailsDataSource {
        return BudgetDetailsRepository.getInstance(BudgetDetailsLocalDataSource.newInstance(context)!!)!!
    }
    fun provideBudgetDestroyInstance() {
        return BudgetDetailsRepository.destroyInstance()
    }
}