package al.bruno.personal.expense.model

import android.os.Parcel
import android.os.Parcelable
import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.databinding.PropertyChangeRegistry
import androidx.room.*

@Entity(tableName = "categories", indices = [Index(value = ["_category", "_sync_time"] , unique = true)])
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
    var type: String? = ""
    @ColumnInfo(name = "_sync_time")
    var syncTime: Long = 0.toLong()

    @Ignore
    //@get:com.google.firebase.database.Exclude
    var propertyChangeRegistry = PropertyChangeRegistry()

    constructor(parcel: Parcel) : this() {
        id = parcel.readLong()
        category = parcel.readString()
        type = parcel.readString()
        syncTime = parcel.readLong()
    }

    constructor(category: String, type: String, syncTime: Long) : this() {
        this.category = category
        this.type = type
        this.syncTime = syncTime
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(id)
        parcel.writeString(category)
        parcel.writeString(type)
        parcel.writeLong(syncTime)
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
        return "$id-$category-$syncTime"
    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        propertyChangeRegistry.remove(callback);
    }
    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        propertyChangeRegistry.add(callback)
    }
}
