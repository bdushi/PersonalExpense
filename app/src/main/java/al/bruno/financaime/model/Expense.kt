package al.bruno.financaime.model

import android.content.ContentValues
import android.database.Cursor
import android.os.Parcel
import android.os.Parcelable
import android.provider.BaseColumns

import java.util.Date

import al.bruno.financaime.util.Utilities
import al.bruno.financaime.util.Utilities.dateFormat
import al.bruno.financaime.util.Utilities.format
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "expense")
class Expense : Parcelable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "_id")
    var id: Long = 0
    @ColumnInfo(name = "_expense_name")
    var expenseName: String? = null
    @ColumnInfo(name = "_expense")
    var expense: Double = 0.toDouble()
    @ColumnInfo(name = "_date")
    var date: Date? = null
    @ColumnInfo(name = "_id_budget")
    var idBudget: Long = 0

    val expensesStr: String
        get() = format(expense)

    val idStr: String
        get() = id.toString()

    val dateStr: String
        get() = dateFormat(date!!)

    constructor() {}

    constructor(expenseName: String, expense: Double, date: Date, id: Long) {
        this.expenseName = expenseName
        this.expense = expense
        this.date = date
        this.id = id
    }

    protected constructor(`in`: Parcel) {
        id = `in`.readLong()
        expenseName = `in`.readString()
        expense = `in`.readDouble()
        date = Date(`in`.readLong())
        idBudget = `in`.readLong()
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeLong(id)
        dest.writeString(expenseName)
        dest.writeDouble(expense)
        dest.writeLong(date!!.time)
        dest.writeLong(idBudget)
    }

    override fun describeContents(): Int {
        return 0
    }

    abstract class ExpenseTable : BaseColumns {
        companion object {

            //emri i tabeles
            val EXPENSE_TABLE = "expense"

            //emrat e kolonave te tabeles
            val EXPENSE_NAME = "_expense_name"
            val EXPENSE = "_expense"
            val DATE = "_date"
            val ID_BUDGET = "_id_budget"


            val CREATE_EXPENSE_TABLE = ("CREATE TABLE " + EXPENSE_TABLE + "(" + BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "
                    + EXPENSE_NAME + " TEXT, "
                    + EXPENSE + " REAL, "
                    + DATE + " INTEGER, "
                    + ID_BUDGET + " INTEGER REFERENCES budget(_id)"
                    + ")")

            fun contentExpense(expense: Expense): ContentValues {
                val contentValues = ContentValues()
                contentValues.put(EXPENSE_NAME, expense.expenseName)
                contentValues.put(EXPENSE, expense.expense)
                contentValues.put(DATE, Utilities.dateToTimestamp(expense.date))
                contentValues.put(ID_BUDGET, expense.idBudget)
                return contentValues
            }

            fun expenseCursor(cursor: Cursor): Expense {
                val expense = Expense()
                expense.id = cursor.getLong(cursor.getColumnIndex(BaseColumns._ID))
                expense.expenseName = cursor.getString(cursor.getColumnIndex(EXPENSE_NAME))
                expense.expense = cursor.getDouble(cursor.getColumnIndex(EXPENSE))
                expense.date = Utilities.fromTimestamp(cursor.getLong(cursor.getColumnIndex(DATE)))
                return expense
            }
        }

    }

    companion object CREATOR : Parcelable.Creator<Expense> {
        override fun createFromParcel(parcel: Parcel): Expense {
            return Expense(parcel)
        }

        override fun newArray(size: Int): Array<Expense?> {
            return arrayOfNulls(size)
        }
    }
}
