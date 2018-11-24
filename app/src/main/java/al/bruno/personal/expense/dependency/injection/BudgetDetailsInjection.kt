package al.bruno.personal.expense.dependency.injection

import al.bruno.personal.expense.data.source.BudgetDetailsDataSource
import al.bruno.personal.expense.data.source.BudgetDetailsRepository
import al.bruno.personal.expense.data.source.local.BudgetDetailsLocalDataSource
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