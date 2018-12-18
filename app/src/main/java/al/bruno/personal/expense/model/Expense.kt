package al.bruno.personal.expense.model

import al.bruno.personal.expense.util.Utilities.dateFormat
import al.bruno.personal.expense.util.Utilities.format
import androidx.room.*
import org.joda.time.DateTime

@Entity(tableName = "expense",
        indices = arrayOf(Index(value = arrayOf("_id_budget", "_date", "_id") , unique = true)),
        foreignKeys = arrayOf(ForeignKey(entity = Incomes::class, parentColumns = arrayOf("_id"), childColumns = arrayOf("_id_budget"), onDelete = ForeignKey.NO_ACTION, onUpdate = ForeignKey.NO_ACTION)))
class Expense() {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "_id")
    var id: Long = 0
    @ColumnInfo(name = "_expense")
    var expense: String? = null
    @ColumnInfo(name = "_amount")
    var amount: Double = 0.0
    @ColumnInfo(name = "_date")
    var date: DateTime? = null
    @ColumnInfo(name = "_id_budget")
    var idBudget: Long = 0

    @Ignore
    var amountStr: String = ""
        get() {
        return format(amount, 0)
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

    override fun toString(): String {
        return "$id-$expense:$amount:$date:$idBudget"
    }
}
