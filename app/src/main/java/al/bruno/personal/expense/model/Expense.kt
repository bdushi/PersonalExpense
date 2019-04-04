package al.bruno.personal.expense.model

import al.bruno.personal.expense.util.Utilities
import al.bruno.personal.expense.util.Utilities.dateFormat
import al.bruno.personal.expense.util.Utilities.format
import android.os.Parcel
import android.os.Parcelable
import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.databinding.PropertyChangeRegistry
import androidx.room.*
import com.google.firebase.database.Exclude
import org.joda.time.DateTime

@Entity(
        tableName = "expense",
        indices = [Index(value = arrayOf("_date", "_id") , unique = true)])
class Expense() : Parcelable, Observable {
    @get:Exclude
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "_id")
    var id: Long = 0
    @ColumnInfo(name = "_type")
    var type: String? = null
    @ColumnInfo(name = "_category")
    var category: String? = null
    @ColumnInfo(name = "_memo")
    var memo: String? = null
    @ColumnInfo(name = "_amount")
    var amount: Double = 0.0
    @ColumnInfo(name = "_date")
    var date: DateTime? = null
        @Bindable
        get
        set(value) {
            field = value
            propertyChangeRegistry.notifyChange(this, al.bruno.personal.expense.BR.date)
            propertyChangeRegistry.notifyChange(this, al.bruno.personal.expense.BR.expenseDate)
        }
    @get:Exclude
    @Ignore
    var amountStr: String = ""
        get() {
        return format(amount, 0)
    }
    @get:Exclude
    @Ignore
    var idStr : String = ""
    get() {
        return id.toString()
    }
    @get:Exclude
    @Ignore
    var dateStr : String = ""
    get() {
        return dateFormat(date!!)
    }
    @get:Exclude
    @Ignore
    var expenseDate : String = ""
        @Bindable
        get() {
            return Utilities.expenseDate(date!!)
        }
    @get:Exclude
    @Ignore
    var propertyChangeRegistry = PropertyChangeRegistry()

    constructor(parcel: Parcel) : this() {
        id = parcel.readLong()
        type = parcel.readString()
        category = parcel.readString()
        memo = parcel.readString()
        amount = parcel.readDouble()
        date = DateTime(parcel.readLong())
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(id)
        parcel.writeString(type)
        parcel.writeString(category)
        parcel.writeString(memo)
        parcel.writeDouble(amount)
        parcel.writeLong(date!!.millis)
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
    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        propertyChangeRegistry.remove(callback);
    }
    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        propertyChangeRegistry.add(callback)
    }
    override fun toString(): String {
        return "$id-$type:$category:$memo:$amount:$date"
    }
}
