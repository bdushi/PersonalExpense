package al.bruno.personal.expense.entities

import al.bruno.personal.expense.model.ExpenseChart
import androidx.room.ColumnInfo
import androidx.room.Relation

class Chart {
    @ColumnInfo(name = "_type_master")
    var type: String? = null
    @Relation(entity = ExpenseChart::class, parentColumn = "_type_master", entityColumn = "_type")
    var expenses: List<ExpenseChart>? = null
}
