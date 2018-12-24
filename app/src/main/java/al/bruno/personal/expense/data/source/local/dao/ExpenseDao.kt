package al.bruno.personal.expense.data.source.local.dao

import al.bruno.personal.expense.model.Expense
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import io.reactivex.Single
import org.joda.time.DateTime
import java.util.*

@Dao
interface ExpenseDao {
    @Insert
    fun insert(expense: Expense) : Single<Long>

    @Query("SELECT * FROM expense WHERE _id = :id")
    fun expense(id: Long) : LiveData<Expense>

    @Query("SELECT _id, TOTAL(_amount) AS _amount, _date FROM expense WHERE strftime('%m', datetime(_date/1000, 'unixepoch')) = :month GROUP BY TRIM(_category)")
    fun expenses(month: String) : LiveData<List<Expense>>

    @Query("SELECT _id, _category, TOTAL(_amount) AS _amount, _date FROM expense WHERE strftime('%m', datetime(_date/1000, 'unixepoch')) = :month AND strftime('%Y', datetime(_date/1000, 'unixepoch')) = :year GROUP BY TRIM(_category)")
    fun expenses(month: String, year: String) : LiveData<List<Expense>>

    @Query("SELECT (SELECT COUNT(DISTINCT(ee._category)) FROM expense AS ee WHERE ee._id >= e._id) AS _id, e._category, TOTAL(e._amount) AS _amount, e._date FROM expense AS e WHERE _date = :date GROUP BY TRIM(e._category) ORDER BY _id")
    fun expenses(date: DateTime) : Single<List<Expense>>

    @Query("SELECT _id, _category, _amount , _date FROM expense WHERE strftime('%m', datetime(_date/1000, 'unixepoch')) = :month AND strftime('%Y', datetime(_date/1000, 'unixepoch')) = :year AND _amount <> 0")
    fun incomes(month: String, year: String) : Single<List<Expense>>

    @Query("SELECT SUM(ee._amount) AS _total FROM expense AS ee WHERE ee._date = :date GROUP BY ee._date")
    fun total(date: DateTime) : Single<String>

    @Query("SELECT _date FROM expense")
    fun date() : Single<Array<DateTime>>
}