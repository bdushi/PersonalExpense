package al.bruno.financaime.model

import android.content.ContentValues
import android.database.Cursor
import android.os.Parcel
import android.os.Parcelable
import android.provider.BaseColumns

import java.util.Date

import al.bruno.financaime.util.Utilities
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlin.math.exp

@Entity(tableName = "budget")
class Budget : Parcelable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "_id")
    private var id: Long = 0
    @ColumnInfo(name = "_budget")
    var budget: Double = 0.toDouble()
    @ColumnInfo(name = "_incomes")
    var incomes: Double = 0.toDouble()
    @ColumnInfo(name = "_date")
    var date: Date? = null
    @ColumnInfo(name = "_expense")
    var expense: Double = 0.toDouble()

    constructor() {}
    constructor(id: Long, budget: Double, incomes: Double, date: Date, expense: Double) {
        this.id = id;
        this.budget = budget;
        this.incomes = incomes;
        this.date = date;
        this.expense = expense
    }

    protected constructor(`in`: Parcel) {
        id = `in`.readLong()
        budget = `in`.readDouble()
        incomes = `in`.readDouble()
        date = Date(`in`.readLong())
        expense = `in`.readDouble()
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeLong(id)
        dest.writeDouble(budget)
        dest.writeDouble(incomes)
        dest.writeLong(date!!.time)
        dest.writeDouble(expense)
    }

    override fun describeContents(): Int {
        return 0
    }

    fun getId(): Long {
        return id
    }

    fun setId(id: Long): Long {
        this.id = id
        return id
    }

    public abstract class BudgetTable : BaseColumns {
        public companion object {

            val BUDGET_TABLE = "budget"

            val BUDGET = "_budget"
            val INCOMES = "_incomes"
            val EXPENSE = "_expense"
            val DATE = "_date"

            public val CREATE_BUDGET_TABLE = ("CREATE TABLE " + BUDGET_TABLE + "("
                    + BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "
                    + BUDGET + " REAL, "
                    + INCOMES + " REAL, "
                    + DATE + " INTEGER "
                    + ")")

            fun contentBudget(budget: Budget): ContentValues {
                val contentValues = ContentValues()
                contentValues.put(BUDGET, budget.budget)
                contentValues.put(INCOMES, budget.incomes)
                contentValues.put(DATE, Utilities.dateToTimestamp(budget.date))
                return contentValues
            }

            fun updateContentBudgetValue(budget: Budget): ContentValues {
                val contentValues = ContentValues()
                contentValues.put(BUDGET, budget.budget)
                return contentValues
            }

            fun updateContentIncomesValue(budget: Budget): ContentValues {
                val contentValues = ContentValues()
                contentValues.put(INCOMES, budget.incomes)
                return contentValues
            }

            fun budgetCursor(cursor: Cursor): Budget {
                val budget = Budget()
                budget.setId(cursor.getLong(cursor.getColumnIndex(BaseColumns._ID)))
                budget.budget = cursor.getDouble(cursor.getColumnIndex(BUDGET))
                budget.incomes = cursor.getDouble(cursor.getColumnIndex(INCOMES))
                budget.date = Utilities.fromTimestamp(cursor.getLong(cursor.getColumnIndex(DATE)))
                budget.expense = cursor.getDouble(cursor.getColumnIndex(EXPENSE))
                return budget
            }
        }
    }

    companion object CREATOR : Parcelable.Creator<Budget> {
        override fun createFromParcel(parcel: Parcel): Budget {
            return Budget(parcel)
        }

        override fun newArray(size: Int): Array<Budget?> {
            return arrayOfNulls(size)
        }
    }
}
