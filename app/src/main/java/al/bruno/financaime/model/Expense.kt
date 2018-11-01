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
    @ColumnInfo(name = "_expense_name")
    var expenseName: String? = null
    @ColumnInfo(name = "_expense")
    var expense: Double = 0.toDouble()
    @ColumnInfo(name = "_date")
    var date: Date? = null
    //@ColumnInfo(name = "_id_budget")
    @Ignore
    var idBudget: Long = 0

    fun expensesStr(): String {
        return format(expense)
    }

    fun idStr() : String {
        return id.toString()
    }

    fun dateStr() : String {
        return dateFormat(date!!)
    }

    /*constructor() : this(0, "", 0.0, Date())
    constructor(id: Long, expenseName: String, expense: Double, date: Date) {
        this.id = id
        this.expenseName = expenseName
        this.expense = expense
        this.date = date
    }*/
}
