package al.bruno.personal.expense.di

import al.bruno.personal.expense.data.source.*
import al.bruno.personal.expense.data.source.local.*
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class DataSourceModule {
    @Singleton
    @Binds
    abstract fun provideExpenseDataSource(dataSource: ExpenseLocalDataSource): ExpenseDataSource

    @Singleton
    @Binds
    abstract fun provideCategoriesDataSource(dataSource: CategoriesLocalDataSource): CategoriesDataSource

    @Singleton
    @Binds
    abstract fun provideExpenseChartDataSource(dataSource: ExpenseChartLocalDataSource): ExpenseChartDataSource

    @Singleton
    @Binds
    abstract fun provideExpenseDetailsDataSource(dataSource: ExpenseDetailsLocalDataSource): ExpenseDetailsDataSource

    @Singleton
    @Binds
    abstract fun provideExpenseMasterDataSource(dataSource: ExpenseMasterLocalDataSource): ExpenseMasterDataSource

    @Singleton
    @Binds
    abstract fun provideSettingsDataSource(dataSource: SettingsLocalDataSource): SettingsDataSource

    @Singleton
    @Binds
    abstract fun provideHomeDataSource(dataSource: HomeLocalDataSource): HomeDataSource
}