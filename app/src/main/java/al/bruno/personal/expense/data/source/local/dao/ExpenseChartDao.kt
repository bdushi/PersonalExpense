package al.bruno.personal.expense.data.source.local.dao

import al.bruno.personal.expense.entities.ExpenseChart
import al.bruno.personal.expense.entities.Chart
import androidx.room.Dao
import androidx.room.Query
import io.reactivex.Single

@Dao
interface ExpenseChartDao {
    @Query("SELECT CASE WHEN e._type = 'incomes' THEN 'incomes' WHEN e._type = 'expenses' THEN  'expenses' ELSE 'balance' END AS _type_master FROM expense e WHERE strftime('%m', datetime(e._date/1000, 'unixepoch')) = :month AND strftime('%Y', datetime(e._date/1000, 'unixepoch')) = :year GROUP BY e._type ORDER BY e._date")
    fun expenseChart(month: String, year: String): Single<List<ExpenseChart>>
    @Query("SELECT _type AS _type_master FROM expense_chart GROUP BY _type")
    fun chart(): Single<List<Chart>>
}
