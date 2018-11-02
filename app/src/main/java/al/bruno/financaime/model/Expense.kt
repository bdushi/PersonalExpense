package al.bruno.financaime.model

import java.util.Date

import al.bruno.financaime.util.Utilities.dateFormat
import al.bruno.financaime.util.Utilities.format
import androidx.room.*

@Entity(tableName = "expense")
class Expense() {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "_id")
    var id: Long = 0
    @ColumnInfo(name = "_expense")
    var expense: String? = null
    @ColumnInfo(name = "_value")
    var value: Double = 0.0
    @ColumnInfo(name = "_date")
    var date: Date? = null
    @ColumnInfo(name = "_id_budget")
    var idBudget: Long = 0

    @Ignore
    var valueStr: String = ""
        get() {
        return format(value, 0)
    }
    @Ignore
    var idStr : String = ""
    get() {
        return id.toString()
    }
    @Ignore
    var dateStr : String = ""
    get() {
        return dateFormat(date!!)
    }

    /*constructor() : this(0, "", 0.0, Date())
    constructor(id: Long, expense: String, value: Double, date: Date) {
        this.id = id
        this.expense = expense
        this.value = value
        this.date = date
    }*/
}
