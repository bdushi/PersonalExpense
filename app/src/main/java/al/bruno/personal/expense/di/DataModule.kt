package al.bruno.personal.expense.di

import al.bruno.personal.expense.data.source.*
import al.bruno.personal.expense.data.source.local.*
import al.bruno.personal.expense.data.source.local.dao.*
import android.app.Application
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
abstract class DataModule {
    @Module
    companion object {
        @JvmStatic
        @Provides
        @Singleton
        fun providesDatabaseName(): String {
            return "financa.db"
        }

        @JvmStatic
        @Provides
        @Singleton
        fun providesDatabaseHelper(app: Application, name: String): AppDatabase {
            return Room
                    .databaseBuilder(app, AppDatabase::class.java, name)
                    .addMigrations(
                            object : Migration(1, 2) {
                                override fun migrate(database: SupportSQLiteDatabase) {
                                    database.execSQL("ALTER TABLE expense ADD COLUMN _memo TEXT")
                                }
                            },
                            object : Migration(2, 3) {
                                override fun migrate(database: SupportSQLiteDatabase) {
                                    database.execSQL("CREATE VIEW expense_chart AS select _amount, _date, _type from (" +
                                            "select TOTAL(_amount) AS _amount, _date, _type from expense where _type = 'expenses' GROUP BY _date " +
                                            "union all " +
                                            "select TOTAL(_amount) AS _amount, _date, _type from expense where _type = 'incomes'  GROUP BY _date " +
                                            "union all " +
                                            "select TOTAL(_amount) AS _amount, _date, 'balance' from (" +
                                            "select _amount, _date, _type from expense where _type = 'expenses' " +
                                            "union all " +
                                            "select -_amount, _date, _type from expense where _type = 'incomes'" +
                                            ") " +
                                            "GROUP BY _date)")
                                }
                            }).build()
        }

        @JvmStatic
        @Singleton
        @Provides
        fun provideExpenseDao(appDatabase: AppDatabase): ExpenseDao {
            return appDatabase.expenseDao()
        }

        @JvmStatic
        @Singleton
        @Provides
        fun provideCategoriesDao(appDatabase: AppDatabase): CategoriesDao {
            return appDatabase.categoriesDao()
        }

        @JvmStatic
        @Singleton
        @Provides
        fun provideExpenseChartDao(appDatabase: AppDatabase): ExpenseChartDao {
            return appDatabase.expenseChartDao();
        }

        @JvmStatic
        @Singleton
        @Provides
        fun provideExpenseDetailsDao(appDatabase: AppDatabase): ExpenseDetailsDao {
            return appDatabase.expenseDetailsDao()
        }

        @JvmStatic
        @Singleton
        @Provides
        fun provideExpenseMasterDao(appDatabase: AppDatabase): ExpenseMasterDao {
            return appDatabase.expenseMasterDao()
        }

        @JvmStatic
        @Singleton
        @Provides
        fun provideSettingsDao(appDatabase: AppDatabase): SettingsDao {
            return appDatabase.settingsDao()
        }

        @JvmStatic
        @Singleton
        @Provides
        fun provideHomeDao(appDatabase: AppDatabase): HomeDao {
            return appDatabase.homeDao()
        }
    }


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