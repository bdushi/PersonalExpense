package al.bruno.personal.expense.model

import al.bruno.personal.expense.util.Utilities.dateFormat
import al.bruno.personal.expense.util.Utilities.format
import android.os.Parcel
import android.os.Parcelable
import androidx.room.*
import org.joda.time.DateTime

@Entity(tableName = "expense", indices = arrayOf(Index(value = arrayOf("_date", "_id") , unique = true)))
class Expense() : Parcelable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "_id")
    var id: Long = 0
    @ColumnInfo(name = "_income")
    var income: Double? = 0.0
    @ColumnInfo(name = "_category")
    var category: String? = null
    @ColumnInfo(name = "_amount")
    var amount: Double = 0.0
    @ColumnInfo(name = "_date")
    var date: DateTime? = null

    @Ignore
    var amountStr: String = ""
        get() {
        return format(amount, 0)
    }
    @Ignore
    var idStr : String = ""
    get() {
        return id.toString()
    }
    @Ignore
    var dateStr : String = ""
    get() {
        return dateFormat(date!!)
    }

    constructor(parcel: Parcel) : this() {
        id = parcel.readLong()
        income = parcel.readValue(Double::class.java.classLoader) as? Double
        category = parcel.readString()
        amount = parcel.readDouble()
    }

    override fun toString(): String {
        return "$id-$income:$category:$amount:$date"
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(id)
        parcel.writeValue(income)
        parcel.writeString(category)
        parcel.writeDouble(amount)
    }

    override fun describeContents(): Int {
        return 0
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
