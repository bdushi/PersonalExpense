package al.bruno.personal.expense.dependency.injection

import al.bruno.personal.expense.data.source.*
import al.bruno.personal.expense.data.source.local.*
import android.content.Context
import androidx.annotation.NonNull

object InjectionProvider {
    fun provideExpenseDetailsInjection(@NonNull context: Context): ExpenseDetailsDataSource {
        val appDatabase :AppDatabase = AppDatabase.getInstance(context)
        return ExpenseDetailsRepository.getInstance(ExpenseDetailsLocalDataSource.getInstance(appDatabase.expenseDetailsDao())!!)!!
    }
    fun provideExpenseMasterInjection(@NonNull context: Context): ExpenseMasterDataSource {
        val appDatabase :AppDatabase = AppDatabase.getInstance(context)
        return ExpenseMasterRepository.getInstance(ExpenseMasterLocalDataSource.getInstance(appDatabase.expenseMasterDao())!!)!!
    }
    fun provideExpenseChartInjection(@NonNull context: Context): ExpenseChartDataSource {
        val appDatabase :AppDatabase = AppDatabase.getInstance(context)
        return ExpenseChartRepository.getInstance(ExpenseChartLocalDataSource.getInstance(appDatabase.expenseChartDao())!!)!!
    }
    fun providerCategoriesInjection(@NonNull context: Context) : CategoriesDataSource? {
        val appDatabase :AppDatabase = AppDatabase.getInstance(context)
        return CategoriesRepository.getInstance(CategoriesLocalDataSource.getInstance(appDatabase.categoriesDao())!!)
    }
    fun providerExpenseInjection(@NonNull context: Context) : ExpenseDataSource? {
        val appDatabase :AppDatabase = AppDatabase.getInstance(context)
        return ExpenseRepository.getInstance(ExpenseLocalDataSource.getInstance(appDatabase.expenseDao())!!)
    }
    fun providerSettingsInjection(@NonNull context: Context) : SettingsDataSource? {
        val appDatabase :AppDatabase = AppDatabase.getInstance(context)
        return SettingsRepository.getInstance(SettingsLocalDataSource.getInstance(appDatabase.settingsDao())!!)
    }
}