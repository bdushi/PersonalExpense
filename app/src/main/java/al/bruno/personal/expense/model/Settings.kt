package al.bruno.personal.expense.model

import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.databinding.PropertyChangeRegistry
import androidx.room.*

@Entity(tableName = "settings", indices = arrayOf(Index(value = arrayOf("_id") , unique = true)))
class Settings() : Observable {
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "_id")
    var id: Long = 0
    @ColumnInfo(name = "_budget")
    var budget: Double = 0.toDouble()
        @Bindable
        get
        set(value) {
            field = value
            propertyChangeRegistry.notifyChange(this, al.bruno.personal.expense.BR.budget)
        }
    @ColumnInfo(name = "_incomes")
    var incomes: Double = 0.toDouble()
        @Bindable
        get
        set(value) {
            field = value
            propertyChangeRegistry.notifyChange(this, al.bruno.personal.expense.BR.incomes)
        }
    @ColumnInfo(name = "_auto")
    var auto: Boolean = false
        @Bindable
        get
        set(value) {
            field = value
            propertyChangeRegistry.notifyChange(this, al.bruno.personal.expense.BR.auto)
        }

    constructor(id:Long) : this() {
        this.id = id
    }
    @Ignore
    private val propertyChangeRegistry = PropertyChangeRegistry()

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        propertyChangeRegistry.remove(callback);
    }
    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        propertyChangeRegistry.add(callback)
    }
}
