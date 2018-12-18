package al.bruno.personal.expense.data.source.local

import al.bruno.personal.expense.data.source.local.dao.*
import al.bruno.personal.expense.model.*
import al.bruno.personal.expense.util.Converter
import android.content.Context
import androidx.room.*
import androidx.room.Database

@Database(
        entities = arrayOf(User::class, Incomes::class, Categories::class, Settings::class, Expense::class),
        views = arrayOf(BudgetDetails::class, ExpenseDetails::class),
        version = 1)
@TypeConverters(Converter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun budgetDao(): IncomesDao
    abstract fun expenseDao(): ExpenseDao
    abstract fun categoriesDao(): CategoriesDao
    abstract fun budgetMasterDao(): BudgetMasterDao
    abstract fun budgetDetailsDao(): BudgetDetailsDao
    abstract fun expenseDetailsDao(): ExpenseDetailsDao
    abstract fun settingsDao() : SettingsDao
    companion object {
        private var INSTANCE: AppDatabase? = null

        private val lock = Any()
        fun getInstance(context: Context): AppDatabase {
            synchronized(lock) {
                if (INSTANCE == null) {
                    INSTANCE = Room
                            .databaseBuilder(context, AppDatabase::class.java, "financa.db")
                            /*.addMigrations(object : Migration(1, 2) {
                                override fun migrate(database: SupportSQLiteDatabase) {
                                    // Since we didn’t alter the table, there’s nothing else
                                    // to do here.
                                }
                            })*/
                            .build()
                }
                return INSTANCE!!
            }
        }
    }
}