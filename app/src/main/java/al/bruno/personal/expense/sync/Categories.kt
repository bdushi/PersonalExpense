package al.bruno.personal.expense.sync

import androidx.room.ColumnInfo

data class Categories constructor(
        @ColumnInfo(name = "_category") val category: String,
        @ColumnInfo(name = "_type") val type: String,
        @ColumnInfo(name = "_sync_time") val syncTime: Long) {
    constructor() : this(category = "", type = "", syncTime =  0.toLong())
}
