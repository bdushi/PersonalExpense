package al.bruno.personal.expense.data.source.local

import al.bruno.personal.expense.data.source.local.dao.*
import al.bruno.personal.expense.model.*
import al.bruno.personal.expense.util.Converter
import android.content.Context
import androidx.room.*
import androidx.room.Database
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(
        entities = arrayOf(User::class, Categories::class, Settings::class, Expense::class),
        views = arrayOf(ExpenseDetails::class, ExpenseChart::class),
        version = 3)
@TypeConverters(Converter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun expenseDao(): ExpenseDao
    abstract fun categoriesDao(): CategoriesDao
    abstract fun expenseDetailsDao(): ExpenseDetailsDao
    abstract fun expenseChartDao(): ExpenseChartDao
    abstract fun expenseMasterDao(): ExpenseMasterDao
    abstract fun settingsDao() : SettingsDao
    abstract fun homeDao() : HomeDao
}