package al.bruno.personal.expense.model

import android.os.Parcel
import android.os.Parcelable
import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.databinding.PropertyChangeRegistry
import androidx.room.*
import com.google.firebase.database.Exclude

@Entity(tableName = "categories", indices = arrayOf(Index(value = arrayOf("_category") , unique = true)))
class Categories() : Observable, Parcelable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "_id")
    var id: Long = 0

    @ColumnInfo(name = "_category")
    var category: String? = ""
    @Bindable
    get
    set(value) {
        field = value
        propertyChangeRegistry.notifyChange(this, al.bruno.personal.expense.BR.category)
    }

    @ColumnInfo(name = "_type")
    var type: String = ""

    @Ignore
    @get:Exclude
    var propertyChangeRegistry = PropertyChangeRegistry()

    constructor(parcel: Parcel) : this() {
        id = parcel.readLong()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(id)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Categories> {
        override fun createFromParcel(parcel: Parcel): Categories {
            return Categories(parcel)
        }

        override fun newArray(size: Int): Array<Categories?> {
            return arrayOfNulls(size)
        }
    }

    override fun toString(): String {
        return "$id-$category"
    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        propertyChangeRegistry.remove(callback);
    }
    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        propertyChangeRegistry.add(callback)
    }
}
