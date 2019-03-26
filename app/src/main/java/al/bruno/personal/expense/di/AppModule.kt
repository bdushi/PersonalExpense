package al.bruno.personal.expense.di

import al.bruno.personal.expense.data.source.local.*
import al.bruno.personal.expense.data.source.local.dao.*
import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    @Singleton
    fun providesSharedPreferences(app: Application): SharedPreferences {
        return PreferenceManager.getDefaultSharedPreferences(app)
    }

    @Provides
    @Singleton
    fun providesDatabaseName(): String {
        return "financa.db"
    }
    @Provides
    @Singleton
    fun providesDatabaseHelper(app: Application): AppDatabase {
        return Room
                .databaseBuilder(app, AppDatabase::class.java, "financa.db")
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
                        })
                .build()
    }

    @Singleton
    @Provides
    fun provideExpenseDao(appDatabase: AppDatabase): ExpenseDao {
        return appDatabase.expenseDao()
    }

    @Singleton
    @Provides
    fun provideCategoriesDao(appDatabase: AppDatabase): CategoriesDao {
        return appDatabase.categoriesDao()
    }

    @Singleton
    @Provides
    fun provideExpenseChartDao(appDatabase: AppDatabase): ExpenseChartDao {
        return appDatabase.expenseChartDao()
    }

    @Singleton
    @Provides
    fun provideExpenseDetailsDao(appDatabase: AppDatabase): ExpenseDetailsDao {
        return appDatabase.expenseDetailsDao()
    }

    @Singleton
    @Provides
    fun provideExpenseMasterDao(appDatabase: AppDatabase): ExpenseMasterDao {
        return appDatabase.expenseMasterDao()
    }

    @Singleton
    @Provides
    fun provideSettingsDao(appDatabase: AppDatabase): SettingsDao {
        return appDatabase.settingsDao()
    }
}