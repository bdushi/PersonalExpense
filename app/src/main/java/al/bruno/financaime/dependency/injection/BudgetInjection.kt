package al.bruno.financaime.dependency.injection

import al.bruno.financaime.data.source.BudgetDataSource
import al.bruno.financaime.data.source.BudgetRepository
import al.bruno.financaime.data.source.local.BudgetLocalDataSource
import android.content.Context
import androidx.annotation.NonNull

object BudgetInjection {
    fun provideBudgetInjection(@NonNull context: Context): BudgetDataSource? {
        return BudgetRepository.getInstance(BudgetLocalDataSource.INSTANCE(context)!!)
    }
}