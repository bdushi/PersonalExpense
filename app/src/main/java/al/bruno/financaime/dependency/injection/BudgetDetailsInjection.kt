package al.bruno.financaime.dependency.injection

import al.bruno.financaime.data.source.BudgetDetailsDataSource
import al.bruno.financaime.data.source.BudgetDetailsRepository
import al.bruno.financaime.data.source.local.BudgetDetailsLocalDataSource
import android.content.Context
import androidx.annotation.NonNull

object BudgetDetailsInjection {
    fun provideBudgetDetailsInjection(@NonNull context: Context): BudgetDetailsDataSource {
        val dataSource = BudgetDetailsRepository.getInstance(BudgetDetailsLocalDataSource.newInstance(context)!!)
        return dataSource!!
    }
    fun provideBudgetDestroyInstance() {
        return BudgetDetailsRepository.destroyInstance()
    }
}