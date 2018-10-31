package al.bruno.financaime.model

import android.content.ContentValues
import android.database.Cursor
import android.provider.BaseColumns
import androidx.room.ColumnInfo

import java.text.DecimalFormat

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "settings")
class Settings {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "_id")
    private var id: Long = 0
    @ColumnInfo(name = "_budget")
    var budget: Double = 0.toDouble()
    @ColumnInfo(name = "_incomes")
    var incomes: Double = 0.toDouble()
    @ColumnInfo(name = "_auto")
    private var auto: Boolean = false

    //ignore
    private val format = DecimalFormat("###.###")

    val incomesStr: String
        get() = format.format(incomes)

    val budgetStr: String
        get() = format.format(budget)

    fun getId(): Long {
        return id
    }

    fun setId(id: Long): Long {
        this.id = id
        return id
    }

    fun isAuto(): Boolean {
        return auto
    }

    fun setAuto(auto: Boolean): Settings {
        this.auto = auto
        return this
    }

    abstract class SettingTable : BaseColumns {
        companion object {

            //emri i tabeles
            val SETTINGS_TABLE = "settings"

            //emrat e kolonave te tabeles
            val INCOMES = "_incomes"
            val BUDGET = "_budget"
            val AUTO = "_auto"


            val CREATE_SETTINGS_TABLE = ("CREATE TABLE " + SETTINGS_TABLE + "(" + BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL UNIQUE, "
                    + INCOMES + " REAL, "
                    + BUDGET + " REAL, "
                    + AUTO + " INTEGER"
                    + ")")

            fun contentSettings(settings: Settings): ContentValues {
                val contentValues = ContentValues()
                contentValues.put(INCOMES, settings.incomes)
                contentValues.put(BUDGET, settings.budget)
                contentValues.put(AUTO, settings.isAuto())
                return contentValues
            }

            fun contentBudgetSettings(settings: Settings): ContentValues {
                val contentValues = ContentValues()
                contentValues.put(BUDGET, settings.budget)
                return contentValues
            }

            fun contentIncomesSettings(settings: Settings): ContentValues {
                val contentValues = ContentValues()
                contentValues.put(INCOMES, settings.incomes)
                return contentValues
            }

            fun contentAutoSettings(settings: Settings): ContentValues {
                val contentValues = ContentValues()
                contentValues.put(AUTO, settings.isAuto())
                return contentValues
            }

            fun settingsCursor(cursor: Cursor): Settings {
                val settings = Settings()
                settings.setId(cursor.getLong(cursor.getColumnIndex(BaseColumns._ID)))
                settings.incomes = cursor.getDouble(cursor.getColumnIndex(INCOMES))
                settings.budget = cursor.getDouble(cursor.getColumnIndex(BUDGET))
                settings.setAuto(cursor.getShort(cursor.getColumnIndex(AUTO)) > 0)
                return settings
            }
        }
    }
}
