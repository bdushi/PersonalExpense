package al.bruno.personal.expense.data.source.local.dao

import al.bruno.personal.expense.entities.Chart
import al.bruno.personal.expense.model.ExpenseDetails
import al.bruno.personal.expense.model.ExpenseMaster
import androidx.room.Dao
import androidx.room.Query
import io.reactivex.Single

@Dao
interface HomeDao {
    @Query("SELECT _type AS _type_master FROM expense_chart GROUP BY _type")
    fun chart(): Single<List<Chart>>
    @Query("SELECT TOTAL(_expense) AS _expenses, TOTAL(_income) AS _income, TOTAL(_balance) AS _balance, 0 AS _remain, _date from (SELECT _amount AS _expense, 0 AS _income, 0 AS _balance,  _date from expense where _type = 'expenses' UNION ALL SELECT 0 AS _expense, _amount AS _income, 0 AS _balance, _date from expense where _type = 'incomes' union all select 0 AS _expense, 0 AS _income, _amount AS _balance,  _date from (select _amount, _date from expense where _type = 'incomes' union all select -_amount, _date from expense where _type = 'expenses')) WHERE strftime('%m', datetime(_date/1000, 'unixepoch')) = :month AND strftime('%Y', datetime(_date/1000, 'unixepoch')) = :year GROUP BY strftime('%m%Y', datetime(_date/1000, 'unixepoch'))")
    fun budgetDetails(month: String , year: String) : Single<ExpenseDetails>
    @Query("SELECT e._id AS _entity_id, e._date AS _master_date, TOTAL(CASE WHEN e._type = 'incomes' THEN  e._amount ELSE 0 END) AS _master_income, TOTAL(CASE WHEN e._type = 'expenses' THEN  e._amount ELSE 0 END) AS _master_expenses FROM expense AS e WHERE strftime('%m', datetime(e._date/1000, 'unixepoch')) = :month AND strftime('%Y', datetime(e._date/1000, 'unixepoch')) = :year GROUP BY e._date ORDER BY e._date")
    fun expenseMaster(month: String, year: String): Single<List<ExpenseMaster>>
}