package al.bruno.personal.expense.data.source.local.dao

import al.bruno.personal.expense.model.ExpenseMaster
import androidx.room.Dao
import androidx.room.Query
import io.reactivex.Single

@Dao
interface ExpenseMasterDao {
    @Query("SELECT e._id AS _entity_id, e._date AS _master_date, TOTAL(CASE WHEN e._type = 'incomes' THEN  e._amount ELSE 0 END) AS _master_income, TOTAL(CASE WHEN e._type = 'expenses' THEN  e._amount ELSE 0 END) AS _master_expenses FROM expense AS e WHERE strftime('%m', datetime(e._date/1000, 'unixepoch')) = :month AND strftime('%Y', datetime(e._date/1000, 'unixepoch')) = :year GROUP BY e._date ORDER BY e._date")
    fun expenseMaster(month: String, year: String): Single<List<ExpenseMaster>>
}