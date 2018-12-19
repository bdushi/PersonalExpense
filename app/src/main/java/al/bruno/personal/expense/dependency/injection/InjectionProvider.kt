package al.bruno.personal.expense.dependency.injection

import al.bruno.personal.expense.data.source.*
import al.bruno.personal.expense.data.source.local.*
import android.content.Context
import androidx.annotation.NonNull

object InjectionProvider {
    fun provideBudgetDetailsInjection(@NonNull context: Context): BudgetDetailsDataSource {
        return BudgetDetailsRepository.getInstance(BudgetDetailsLocalDataSource.INSTANCE(context)!!)!!
    }
    fun provideBudgetDestroyInstance() {
        return BudgetDetailsRepository.destroyInstance()
    }
    fun providerCategoriesInjection(@NonNull context: Context) : CategoriesDataSource? {
        return CategoriesRepository.getInstance(CategoriesLocalDataSource.INSTANCE(context)!!)
    }
    fun providerExpenseInjection(@NonNull context: Context) : ExpenseDataSource? {
        return ExpenseRepository.getInstance(ExpenseLocalDataSource.INSTANCE(context)!!)
    }
    fun providerSettingsInjection(@NonNull context: Context) : SettingsDataSource? {
        return SettingsRepository.getInstance(SettingsLocalDataSource.INSTANCE(context)!!)
    }
}