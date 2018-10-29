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
class Categories : Parcelable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "_id")
    var id: Long = 0
    @ColumnInfo(name = "_category")
    private var category: String? = null

    constructor() {}

    constructor(category: String) {
        this.category = category
    }


    protected constructor(`in`: Parcel) {
        id = `in`.readLong()
        category = `in`.readString()
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeLong(id)
        dest.writeString(category)
    }

    override fun describeContents(): Int {
        return 0
    }

    fun getCategory(): String? {
        return category
    }

    fun setCategory(category: String): Categories {
        this.category = category
        return this
    }

    override fun toString(): String {
        return category!!
    }

    //DDL

    abstract class CategoryTable : BaseColumns {
        companion object {
            val CATEGORY_TABLE = "category"

            val CATEGORY = "_category"

            val CREATE_CATEGORY_TABLE = ("CREATE TABLE " + CATEGORY_TABLE + "(" + BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "
                    + CATEGORY + " TEXT" + ")")

            fun contentCategory(categories: Categories): ContentValues {
                val contentValues = ContentValues()
                contentValues.put(CATEGORY, categories.getCategory())

                return contentValues
            }

            fun categoryCursor(cursor: Cursor): Categories {
                val category = Categories()
                category.id = cursor.getLong(cursor.getColumnIndex(BaseColumns._ID))
                category.setCategory(cursor.getString(cursor.getColumnIndex(CATEGORY)))

                return category
            }
        }
    }

    companion object CREATOR : Parcelable.Creator<Categories> {
        override fun createFromParcel(parcel: Parcel): Categories {
            return Categories(parcel)
        }

        override fun newArray(size: Int): Array<Categories?> {
            return arrayOfNulls(size)
        }
    }
}
