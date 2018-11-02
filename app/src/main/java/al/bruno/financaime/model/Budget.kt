package al.bruno.financaime.model

import al.bruno.financaime.util.Utilities.format
import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.room.*
import java.util.Date
import androidx.databinding.PropertyChangeRegistry



@Entity(tableName = "budget")
class Budget() : Observable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "_id")
    var id: Long = 0
    @ColumnInfo(name = "_budget")
    var budget: Double = 0.toDouble()
    @ColumnInfo(name = "_incomes")
    var incomes: Double = 0.toDouble()
    @ColumnInfo(name = "_date")
    var date: Date? = null

    @Ignore
    var budgetStr: String = ""
    get() {
        return format(budget, 0)
    }
    @Ignore
    private val propertyChangeRegistry = PropertyChangeRegistry()

    @Ignore
    var value: Double = 0.0
        @Bindable
        get() {
            return field
        }
        set(value) {
            field = value
            propertyChangeRegistry.notifyChange(this, al.bruno.financaime.BR.value)
        }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        propertyChangeRegistry.remove(callback);
    }
    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        propertyChangeRegistry.add(callback)
    }

    /*constructor() : this(0, 0.0, 0.0, Date(), 0.0)
    constructor(id: Long, budget: Double, incomes: Double, date: Date, value: Double) {
        this.id = id;
        this.budget = budget;
        this.incomes = incomes;
        this.date = date;
        this.value = value
    }*/
}
