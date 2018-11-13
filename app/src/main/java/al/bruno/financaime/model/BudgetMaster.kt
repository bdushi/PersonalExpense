package al.bruno.financaime.model

import androidx.room.ColumnInfo
import androidx.room.Embedded

class BudgetMaster {
    @Embedded
    var budget: Budget? = null
    @ColumnInfo(name = "_amount")
    var amount:Double = 0.0
    //@Relation(entity = Expense::class, parentColumn = "_id", entityColumn = "_id_budget")
    //@Embedded(prefix = "_expense")
    //var expense: List<Expense>? = null
}