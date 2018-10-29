package al.bruno.financaime.model

import android.content.ContentValues
import android.database.Cursor
import android.os.Parcel
import android.os.Parcelable
import android.provider.BaseColumns
import androidx.room.ColumnInfo

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "category")
class Category : Parcelable {
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

    fun setCategory(category: String): Category {
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

            fun contentCategory(category: Category): ContentValues {
                val contentValues = ContentValues()
                contentValues.put(CATEGORY, category.getCategory())

                return contentValues
            }

            fun categoryCursor(cursor: Cursor): Category {
                val category = Category()
                category.id = cursor.getLong(cursor.getColumnIndex(BaseColumns._ID))
                category.setCategory(cursor.getString(cursor.getColumnIndex(CATEGORY)))

                return category
            }
        }
    }

    companion object CREATOR : Parcelable.Creator<Category> {
        override fun createFromParcel(parcel: Parcel): Category {
            return Category(parcel)
        }

        override fun newArray(size: Int): Array<Category?> {
            return arrayOfNulls(size)
        }
    }
}
