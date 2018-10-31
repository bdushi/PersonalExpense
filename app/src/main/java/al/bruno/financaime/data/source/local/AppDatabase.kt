package al.bruno.financaime.data.source.local

import al.bruno.financaime.model.*
import android.content.Context
import androidx.databinding.adapters.Converters
import androidx.room.*
import androidx.room.Database
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.room.migration.Migration



@Database(
        entities = arrayOf(User::class, Budget::class, Categories::class, Settings::class, Expense::class),
        views = arrayOf(BudgetDetails::class, ExpenseDetails::class),
        version = 1)
@TypeConverters(Converters::class)
public abstract class AppDatabase : RoomDatabase() {

    companion object {
        private var INSTANCE: AppDatabase? = null

        private val lock = Any()
        fun getInstance(context: Context): AppDatabase {
            synchronized(lock) {
                if (INSTANCE == null) {
                    INSTANCE = Room
                            .databaseBuilder(context.applicationContext, AppDatabase::class.java, "financa.db")
                            .addMigrations(object : Migration(1, 2) {
                                override fun migrate(database: SupportSQLiteDatabase) {
                                    // Since we didn’t alter the table, there’s nothing else
                                    // to do here.
                                }
                            })
                            .build()
                }
                return INSTANCE!!
            }
        }
    }
}