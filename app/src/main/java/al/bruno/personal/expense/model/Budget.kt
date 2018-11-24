package al.bruno.personal.expense.model

import al.bruno.personal.expense.callback.OnItemSelectedListener
import al.bruno.personal.expense.util.Utilities.format
import android.os.Parcel
import android.os.Parcelable
import android.view.View
import android.widget.AdapterView
import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.room.*
import java.util.Date
import androidx.databinding.PropertyChangeRegistry

@Entity(tableName = "budget",  indices = arrayOf(Index(value = arrayOf("_date") , unique = true)))
class Budget() : Observable, OnItemSelectedListener, Parcelable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "_id")
    var id: Long = 0
    @ColumnInfo(name = "_budget")
    var budget: Double = 0.0
        @Bindable
        get
        set(value){
            field = value
            propertyChangeRegistry.notifyChange(this, al.bruno.personal.expense.BR.budget)
        }
    @ColumnInfo(name = "_incomes")
    var incomes: Double = 0.0
        @Bindable
        get
        set(value) {
            field = value
            propertyChangeRegistry.notifyChange(this, al.bruno.personal.expense.BR.incomes)
        }
    @ColumnInfo(name = "_date")
    var date: Date? = null

    /*@Embedded(prefix = "_expense")
    var expense: Expense? = null*/

    @Ignore
    var budgetStr: String = ""
    get() {
        return format(budget, 0)
    }
    @Ignore
    var incomesStr: String = ""
        get() {
            return format(incomes, 0)
        }

    @Ignore
    var expenseStr: String = ""

    @Ignore
    var amount: Double = 1.0
        @Bindable
        get
        set(value) {
            field = value
            propertyChangeRegistry.notifyChange(this, al.bruno.personal.expense.BR.amount)
        }

    @Ignore
    private val propertyChangeRegistry = PropertyChangeRegistry()

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        propertyChangeRegistry.remove(callback);
    }
    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        propertyChangeRegistry.add(callback)
    }
    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        expenseStr = (p0!!.getItemAtPosition(p2) as Categories).category!!
    }

    constructor(parcel: Parcel) : this() {
        id = parcel.readLong()
        budget = parcel.readDouble()
        incomes = parcel.readDouble()
        date = Date(parcel.readLong())
        amount = parcel.readDouble();
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(id)
        parcel.writeDouble(budget)
        parcel.writeDouble(incomes)
        parcel.writeLong(date!!.time)
        parcel.writeDouble(amount)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Budget> {
        override fun createFromParcel(parcel: Parcel): Budget {
            return Budget(parcel)
        }

        override fun newArray(size: Int): Array<Budget?> {
            return arrayOfNulls(size)
        }
    }

    override fun toString(): String {
        return "Budget:" + id + "_" + date + "_" + budget + "_" + incomes
    }
}
