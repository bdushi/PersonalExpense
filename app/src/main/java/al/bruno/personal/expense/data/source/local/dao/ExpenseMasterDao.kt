package al.bruno.personal.expense.data.source.local.dao

import al.bruno.personal.expense.model.ExpenseMaster
import androidx.room.Dao
import androidx.room.Query
import io.reactivex.Single

@Dao
interface ExpenseMasterDao {
    //(SELECT COUNT(*) from expense AS b  WHERE e._id >= b._id)
    @Query("SELECT e._id AS _entity_id, e._date AS _master_date, TOTAL(CASE WHEN e._type = 'incomes' THEN  e._amount ELSE 0 END) AS _master_income, TOTAL(CASE WHEN e._type = 'expenses' THEN  e._amount ELSE 0 END) AS _master_expenses FROM expense AS e GROUP BY e._date ORDER BY e._date")
    fun expenseMaster(): Single<List<ExpenseMaster>>
}