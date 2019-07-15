package al.bruno.personal.expense.model

import al.bruno.calendar.view.adapter.BindingInterface
import al.bruno.calendar.view.adapter.CustomAdapter
import al.bruno.personal.expense.R
import al.bruno.personal.expense.databinding.LogSingleItemBinding
import al.bruno.personal.expense.util.Utilities.dayFormat
import androidx.room.ColumnInfo
import androidx.room.Ignore
import androidx.room.Relation
import org.joda.time.DateTime

//@DatabaseView("SELECT _id, _type, _category, _amount, (_date / 10000) * 10000 AS _date FROM expense", viewName = "expense_master")
class ExpenseMaster {
    @ColumnInfo(name = "_entity_id")
    var id: Long = 0
    @ColumnInfo(name = "_master_date")
    var date: DateTime? = null
    @ColumnInfo(name = "_master_income")
    var incomes: String? = null
    @ColumnInfo(name = "_master_expenses")
    var expense: String? = null
    @Relation(entity = Expense::class, parentColumn = "_master_date", entityColumn = "_date")
    var expenses: List<Expense>? = null
    /*@Ignore
    var masterAdapter: CustomAdapter<Expense, LogSingleItemBinding>? = null*/
    @Ignore
    var dateStr : String = ""
        get() {
            return dayFormat(date!!)
        }
    fun adapter(): CustomAdapter<Expense, LogSingleItemBinding> {
       return CustomAdapter(expenses, R.layout.log_single_item, BindingInterface<Expense, LogSingleItemBinding> { t, vm -> vm.expense = t })
    }
    override fun toString(): String {
        return "$id-$incomes:$expense:$expenses:$date"
    }
}
