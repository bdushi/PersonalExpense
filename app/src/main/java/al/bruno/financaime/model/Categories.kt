package al.bruno.financaime.model

import android.content.ContentValues
import android.database.Cursor
import android.os.Parcel
import android.os.Parcelable
import android.provider.BaseColumns
import androidx.room.ColumnInfo

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "categories")
class Categories() {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "_id")
    var id: Long = 0
    @ColumnInfo(name = "_category")
    var category: String? = null

    /*constructor() : this(0, "")
    constructor(id:Long, category: String) {
        this.id = id
        this.category = category
    }*/
}
