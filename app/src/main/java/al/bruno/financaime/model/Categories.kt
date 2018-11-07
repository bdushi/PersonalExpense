package al.bruno.financaime.model

import androidx.room.*

//, indices = arrayOf(Index(value = arrayOf("_category"), unique = true))
@Entity(tableName = "categories")
class Categories {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "_id")
    var id: Long = 0
    @Ignore
    @ColumnInfo(name = "_category")
    var category: String? = null
    /*@Bindable
    get() {
        return field
    }
    set(value) {
        field = value
        propertyChangeRegistry.notifyChange(this, al.bruno.financaime.BR.category)
    }*/

    constructor(category: String) {
        this.category = category
    }
    constructor()
    /*constructor() : this(0, "")
    constructor(id:Long, category: String) {
        this.id = id
        this.category = category
    }*/
    /*override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        propertyChangeRegistry.remove(callback);
    }
    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        propertyChangeRegistry.add(callback)
    }*/
}
