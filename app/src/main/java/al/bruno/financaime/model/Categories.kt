package al.bruno.financaime.model

import androidx.room.ColumnInfo

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "categories", indices = arrayOf(Index(value = arrayOf("_category") , unique = true)))
class Categories {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "_id")
    var id: Long = 0
    @ColumnInfo(name = "_category")
    var category: String? = null

    constructor(category: String) {
        this.category = category
    }
    constructor()
    /*constructor() : this(0, "")
    constructor(id:Long, category: String) {
        this.id = id
        this.category = category
    }*/
}
