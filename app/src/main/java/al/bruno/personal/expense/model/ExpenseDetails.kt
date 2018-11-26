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
        "e._expense AS _expense," +
        "TOTAL(e._amount) AS _amount," +
        "e._date AS _date," +
        "(SELECT COUNT(*) FROM expense AS ee WHERE ee._id >= e._id) AS _id," +
        "(SELECT SUM(ee._amount) FROM expense AS ee GROUP BY ee._date) AS _total " +
        "FROM expense AS e GROUP BY TRIM(e._expense) ORDER BY _id ASC", viewName = "expense_details")
class ExpenseDetails() {
    @ColumnInfo(name = "_id")
    var id:Long = 0
    @ColumnInfo(name = "_total")
    var total:String? = null
    //, projection = arrayOf("_id", "_expense", "_amount", "_date")
    // entityColumn = row_id(_id_budget) used by room to get all rows from expense
    @Relation(entity = Expense::class, parentColumn = "_id", entityColumn = "_id_budget")
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
