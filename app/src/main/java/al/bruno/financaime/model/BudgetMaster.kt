package al.bruno.financaime.model

import androidx.room.Embedded
import androidx.room.Relation

class BudgetMaster {
    @Embedded
    var budget: Budget? = null
    //var value: Double = 0.0
    @Relation(entity = Expense::class, parentColumn = "id", entityColumn = "_id_budget")
    var expense: Expense? = null
}