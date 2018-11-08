package al.bruno.financaime.model

import android.os.Parcel
import android.os.Parcelable
import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.databinding.PropertyChangeRegistry
import androidx.room.*

@Entity(tableName = "categories", indices = arrayOf(Index(value = arrayOf("_category") , unique = true)))
class Categories() : Observable, Parcelable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "_id")
    var id: Long = 0

    @ColumnInfo(name = "_category")
    var category: String? = null
    @Bindable
    get
    set(value) {
        field = value
        propertyChangeRegistry.notifyChange(this, al.bruno.financaime.BR.category)
    }

    @Ignore
    var propertyChangeRegistry = PropertyChangeRegistry()

    /*constructor() : this(0, "")
        constructor(id:Long, category: String) {
            this.id = id
            this.category = category
        }*/
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

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        propertyChangeRegistry.remove(callback);
    }
    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        propertyChangeRegistry.add(callback)
    }


}
