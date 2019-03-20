package al.bruno.personal.expense.data.source.local.dao

import al.bruno.personal.expense.model.ExpenseDetails
import androidx.room.Dao
import androidx.room.Query
import io.reactivex.Single

@Dao
interface ExpenseDetailsDao {
    //@Query("SELECT * FROM expense_details WHERE strftime('%m', datetime(_date/1000, 'unixepoch')) = :month AND strftime('%Y', datetime(_date/1000, 'unixepoch')) = :year")
    @Query("SELECT TOTAL(_expense) AS _expenses, TOTAL(_income) AS _income, TOTAL(_balance) AS _balance, 0 AS _remain, _date from (SELECT _amount AS _expense, 0 AS _income, 0 AS _balance,  _date from expense where _type = 'expenses' UNION ALL SELECT 0 AS _expense, _amount AS _income, 0 AS _balance, _date from expense where _type = 'incomes' union all select 0 AS _expense, 0 AS _income, _amount AS _balance,  _date from (select _amount, _date from expense where _type = 'incomes' union all select -_amount, _date from expense where _type = 'expenses')) WHERE strftime('%m', datetime(_date/1000, 'unixepoch')) = :month AND strftime('%Y', datetime(_date/1000, 'unixepoch')) = :year GROUP BY strftime('%m%Y', datetime(_date/1000, 'unixepoch'))")
    fun budgetDetails(month: String , year: String) : Single<ExpenseDetails>
}