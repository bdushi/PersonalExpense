package al.bruno.personal.expense.sync

import androidx.room.ColumnInfo

data class Expense constructor(
        @ColumnInfo(name = "_type") var type: String,
        @ColumnInfo(name = "_category") var category: String,
        @ColumnInfo(name = "_memo") var memo: String,
        @ColumnInfo(name = "_amount") var amount: Double,
        @ColumnInfo(name = "_date") var date: Long,
        @ColumnInfo(name = "_sync_time") val syncTime: Long) {
    constructor() : this(type = "", category = "", memo = "", amount = 0.0, date = 0, syncTime =  0.toLong())
}
