package al.bruno.financaime.dependency.injection

import al.bruno.financaime.data.source.BudgetRepository
import al.bruno.financaime.data.source.local.BudgetLocalDataSource
import android.content.Context
import androidx.annotation.NonNull

object BudgetInjection {
    fun provideIBudgetInjection(@NonNull context: Context) {
        BudgetRepository.getInstance(BudgetLocalDataSource.INSTANCE(context)!!)
    }
}