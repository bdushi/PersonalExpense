package al.bruno.personal.expense.entities

import al.bruno.personal.expense.model.Expense
import androidx.room.ColumnInfo
import androidx.room.Relation

class ExpenseChart {
    @ColumnInfo(name = "_type_master")
    var type: String? = null
    @Relation(entity = Expense::class, parentColumn = "_type_master", entityColumn = "_type")
    var expenses: List<Expense>? = null
}
