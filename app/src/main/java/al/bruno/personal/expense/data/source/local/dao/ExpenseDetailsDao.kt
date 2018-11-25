package al.bruno.personal.expense.data.source.local.dao

import al.bruno.personal.expense.model.ExpenseDetails
import androidx.room.Dao
import androidx.room.Query
import io.reactivex.Single

@Dao
interface ExpenseDetailsDao {
    @Query("SELECT * FROM expense_details")
    fun expense(): Single<ExpenseDetails>
}