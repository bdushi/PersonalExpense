package al.bruno.personal.expense.dependency.injection

import al.bruno.personal.expense.data.source.BudgetDataSource
import al.bruno.personal.expense.data.source.BudgetRepository
import al.bruno.personal.expense.data.source.local.BudgetLocalDataSource
import android.content.Context
import androidx.annotation.NonNull

object BudgetInjection {
    fun provideBudgetInjection(@NonNull context: Context): BudgetDataSource? {
        return BudgetRepository.getInstance(BudgetLocalDataSource.INSTANCE(context)!!)
    }
}