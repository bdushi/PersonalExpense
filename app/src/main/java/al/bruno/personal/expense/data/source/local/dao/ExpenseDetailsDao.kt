package al.bruno.personal.expense.data.source.local.dao

import al.bruno.personal.expense.model.ExpenseDetails
import androidx.room.Dao
import androidx.room.Query
import io.reactivex.Single

@Dao
interface ExpenseDetailsDao {
    //@Query("SELECT * FROM expense_details")
    //(SELECT COUNT(*) FROM expense AS ee WHERE ee._id >= e._id)
    @Query("SELECT " +
            "e._expense AS _expense," +
            "TOTAL(e._amount) AS _amount," +
            "e._date AS _date," +
            "e._id_budget AS _id," +
            "(SELECT SUM(ee._amount) FROM expense AS ee GROUP BY ee._date) AS _total " +
            "FROM expense AS e " +
            "GROUP BY GROUP BY TRIM(e._expense) " +
            "ORDER BY _id ASC")
    fun expense(): Single<ExpenseDetails>
}