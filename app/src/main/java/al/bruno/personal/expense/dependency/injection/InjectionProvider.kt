package al.bruno.personal.expense.dependency.injection

import al.bruno.personal.expense.data.source.*
import al.bruno.personal.expense.data.source.local.*
import android.content.Context
import androidx.annotation.NonNull

object InjectionProvider {
    fun provideBudgetDetailsInjection(@NonNull context: Context): BudgetDetailsDataSource {
        val appDatabase = AppDatabase.getInstance(context)
        return BudgetDetailsRepository.getInstance(BudgetDetailsLocalDataSource.getInstance(appDatabase.budgetDetailsDao())!!)!!
    }
    fun provideBudgetDestroyInstance() {
        return BudgetDetailsRepository.destroyInstance()
    }
    fun provideBudgetInjection(@NonNull context: Context): BudgetDataSource? {
        val appDatabase = AppDatabase.getInstance(context);
        return BudgetRepository.getInstance(BudgetLocalDataSource.getInstance(appDatabase.budgetDao())!!)
    }
    fun providerBudgetMasterInjection(context: Context): BudgetMasterDataSource? {
        val appDatabase = AppDatabase.getInstance(context);
        return BudgetMasterRepository.getInstance(BudgetMasterLocalDataSource.getInstance(appDatabase.budgetMasterDao())!!)
    }
    fun providerCategoriesInjection(@NonNull context: Context) : CategoriesDataSource? {
        val appDatabase = AppDatabase.getInstance(context);
        return CategoriesRepository.getInstance(CategoriesLocalDataSource.getInstance(appDatabase.categoriesDao())!!)
    }
    fun providerExpenseInjection(@NonNull context: Context) : ExpenseDataSource? {
        val appDatabase = AppDatabase.getInstance(context);
        return ExpenseRepository.getInstance(ExpenseLocalDataSource.getInstance(appDatabase.expenseDao())!!)
    }
    fun providerSettingsInjection(@NonNull context: Context) : SettingsDataSource? {
        val appDatabase = AppDatabase.getInstance(context);
        return SettingsRepository.getInstance(SettingsLocalDataSource.getInstance(appDatabase.settingsDao())!!)
    }
    fun providerExpenseDetailsInjection(@NonNull context: Context): ExpenseDetailsDataSource? {
        val appDatabase = AppDatabase.getInstance(context);
        return ExpenseDetailsRepository.getInstance(ExpenseDetailsLocalDataSource.getInstance(appDatabase.expenseDetailsDao())!!)
    }
}