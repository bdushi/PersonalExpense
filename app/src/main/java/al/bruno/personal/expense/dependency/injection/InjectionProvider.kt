package al.bruno.personal.expense.dependency.injection

import al.bruno.personal.expense.data.source.*
import al.bruno.personal.expense.data.source.local.*
import androidx.annotation.NonNull

object InjectionProvider {
    fun provideExpenseDetailsInjection(@NonNull appDatabase :AppDatabase): ExpenseDetailsDataSource {
        return ExpenseDetailsRepository.getInstance(ExpenseDetailsLocalDataSource.getInstance(appDatabase.expenseDetailsDao())!!)!!
    }
    fun provideExpenseMasterInjection(@NonNull appDatabase :AppDatabase): ExpenseMasterDataSource {
        return ExpenseMasterRepository.getInstance(ExpenseMasterLocalDataSource.getInstance(appDatabase.expenseMasterDao())!!)!!
    }
    fun provideExpenseChartInjection(@NonNull appDatabase :AppDatabase): ExpenseChartDataSource {
        return ExpenseChartRepository.getInstance(ExpenseChartLocalDataSource.getInstance(appDatabase.expenseChartDao())!!)!!
    }
    fun providerCategoriesInjection(@NonNull appDatabase :AppDatabase) : CategoriesDataSource? {
        return CategoriesRepository.getInstance(CategoriesLocalDataSource.getInstance(appDatabase.categoriesDao())!!)
    }
    fun providerSettingsInjection(@NonNull appDatabase :AppDatabase) : SettingsDataSource? {
        return SettingsRepository.getInstance(SettingsLocalDataSource.getInstance(appDatabase.settingsDao())!!)
    }
}