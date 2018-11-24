package al.bruno.financaime.dependency.injection

import al.bruno.personal.expense.data.source.*
import al.bruno.personal.expense.data.source.local.*
import android.content.Context
import androidx.annotation.NonNull

object InjectionProvider {
    fun provideBudgetDetailsInjection(@NonNull context: Context): BudgetDetailsDataSource {
        return BudgetDetailsRepository.getInstance(BudgetDetailsLocalDataSource.newInstance(context)!!)!!
    }
    fun provideBudgetDestroyInstance() {
        return BudgetDetailsRepository.destroyInstance()
    }
    fun provideBudgetInjection(@NonNull context: Context): BudgetDataSource? {
        return BudgetRepository.getInstance(BudgetLocalDataSource.INSTANCE(context)!!)
    }
    fun providerBudgetMasterInjection(context: Context): BudgetMasterDataSource? {
        return BudgetMasterRepository.getInstance(BudgetMasterLocalDataSource.newInstance(context)!!)
    }
    fun providerCategoriesInjection(@NonNull context: Context) : CategoriesDataSource? {
        return CategoriesRepository.getInstance(CategoriesLocalDataSource.newInstance(context)!!)
    }
    fun providerExpenseInjection(@NonNull context: Context) : ExpenseDataSource? {
        return ExpenseRepository.newInstance(ExpenseLocalDataSource.INSTANCE(context)!!)
    }
    fun providerSettingsInjection(@NonNull context: Context) : SettingsDataSource? {
        return SettingsRepository.getInstance(SettingsLocalDataSource.getInstance(context)!!)
    }
}