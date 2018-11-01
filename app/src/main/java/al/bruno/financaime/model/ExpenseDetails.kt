package al.bruno.financaime.model

import java.util.ArrayList

import androidx.room.DatabaseView
import androidx.room.Ignore

@DatabaseView("SELECT " +
        "e._id AS _id, " +
        "e._expense AS _expense, " +
        "e._value AS _value, " +
        "e._date AS _date, " +
        "(SELECT sum(_value) FROM expense WHERE _date = e._date) AS _total " +
        "FROM expense AS e", viewName = "expense_details")
class ExpenseDetails() {
    var id: Long = 1
    var total = "0"
    @Ignore
    var expenses: ArrayList<Expense> = ArrayList()

    /*constructor() : this(0, "0", ArrayList())
    constructor(id:Long, total:String, expenses: ArrayList<Expense>) {
        this.id = id;
        this.total = total
        this.expenses = expenses
    }*/
}
