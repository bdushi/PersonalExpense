package al.bruno.personal.expense.model

import al.bruno.personal.expense.observer.Observer
import al.bruno.personal.expense.observer.Subject
import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.databinding.PropertyChangeRegistry
import androidx.room.*

@Entity(tableName = "settings", indices = arrayOf(Index(value = arrayOf("_id") , unique = true)))
class Settings() : Observable, Subject<Settings> {
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
            notifyObserver(this)
        }
    @ColumnInfo(name = "_incomes")
    var incomes: Double = 0.toDouble()
        @Bindable
        get
        set(value) {
            field = value
            propertyChangeRegistry.notifyChange(this, al.bruno.personal.expense.BR.incomes)
            notifyObserver(this)
        }
    @ColumnInfo(name = "_auto")
    var auto: Boolean = false
        @Bindable
        get
        set(value) {
            field = value
            propertyChangeRegistry.notifyChange(this, al.bruno.personal.expense.BR.auto)
            notifyObserver(this)
        }

    constructor(id:Long) : this() {
        this.id = id
    }
    @Ignore
    private val propertyChangeRegistry = PropertyChangeRegistry()
    @Ignore

    private var observer = ArrayList<Observer<Settings>>()

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        propertyChangeRegistry.remove(callback);
    }
    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        propertyChangeRegistry.add(callback)
    }

    override fun registerObserver(o: Observer<Settings>) {
        observer.add(o)
    }

    override fun removeObserver(o: Observer<Settings>) {
        if(observer.indexOf(o) >= 0)
            observer.remove(o);
    }

    override fun notifyObserver(t: Settings) {
        for (o in observer) {
            o.update(t)
        }
    }

    override fun toString(): String {
        return "$id-$budget:$incomes:$auto"
    }
}
