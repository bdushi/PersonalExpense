package al.bruno.personal.expense.model

import al.bruno.personal.expense.R
import al.bruno.personal.expense.adapter.CustomAdapter
import al.bruno.personal.expense.callback.BindingData
import al.bruno.personal.expense.databinding.ExpenseSingleItemBinding
import androidx.room.ColumnInfo

import androidx.room.DatabaseView
import androidx.room.Ignore
import androidx.room.Relation
import java.util.ArrayList

@DatabaseView("SELECT " +
        "e._expense AS _expense, " +
        "e._amount AS _amount, " +
        "e._date AS _date, " +
        "(SELECT COUNT(*) FROM expense AS ee WHERE ee._id >= e._id) AS _id, " +
        "(SELECT TOTAL(ee._amount) FROM expense AS ee WHERE ee._expense = e._expense) AS _total " +
        "FROM expense AS e " +
        "GROUP BY TRIM(e._expense)" +
        "ORDER BY _id ASC", viewName = "expense_details")
class ExpenseDetails() {
    @ColumnInfo(name = "_id")
    var id:Long = 0
    @ColumnInfo(name = "_total")
    var total:String? = null
    //, projection = arrayOf("_id", "_expense", "_amount", "_date")
    @Relation(entity = Expense::class, parentColumn = "_id",entityColumn = "_id")
    var expenses: List<Expense> = ArrayList()
    @Ignore
    var adapter : CustomAdapter<Expense, ExpenseSingleItemBinding>? = null
    get(){
        return CustomAdapter(expenses, R.layout.expense_single_item, object : BindingData<Expense, ExpenseSingleItemBinding> {
            override fun bindData(t: Expense, vm: ExpenseSingleItemBinding) {
                vm.expense = t
            }
        })
    }

    override fun toString(): String {
        return "$id-$total:$expenses"
    }
}
