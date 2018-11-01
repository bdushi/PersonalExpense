package al.bruno.financaime.data.source.local.dao

import al.bruno.financaime.model.BudgetDetails
import androidx.room.Dao
import androidx.room.Query
import io.reactivex.Single

@Dao
interface BudgetDetailsDao {
    /*@Query("SELECT * FROM budget")
    fun budgetDetails(month: String , year: String) : Single<BudgetDetails>*/
}