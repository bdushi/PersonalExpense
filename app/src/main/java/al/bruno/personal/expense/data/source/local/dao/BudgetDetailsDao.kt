package al.bruno.personal.expense.data.source.local.dao

import al.bruno.personal.expense.model.BudgetDetails
import androidx.room.Dao
import androidx.room.Query
import io.reactivex.Single

@Dao
interface BudgetDetailsDao {
    @Query("SELECT * FROM budget_details WHERE strftime('%m', datetime(_date/1000, 'unixepoch')) = :month AND strftime('%Y', datetime(_date/1000, 'unixepoch')) = :year")
    fun budgetDetails(month: String , year: String) : Single<BudgetDetails>
}