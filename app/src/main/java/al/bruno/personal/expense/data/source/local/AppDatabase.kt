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
    companion object {
        private var INSTANCE: AppDatabase? = null
        private val lock = Any()

        fun getInstance(context: Context): AppDatabase {
            synchronized(lock) {
                if (INSTANCE == null) {
                    INSTANCE = Room
                            .databaseBuilder(context, AppDatabase::class.java, "financa.db")
                            .addMigrations(object : Migration(1, 2) {
                                override fun migrate(database: SupportSQLiteDatabase) {
                                    database.execSQL("ALTER TABLE expense ADD COLUMN _memo TEXT")
                                }
                            }, object : Migration(2, 3) {
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
                return INSTANCE!!
            }
        }
    }
}