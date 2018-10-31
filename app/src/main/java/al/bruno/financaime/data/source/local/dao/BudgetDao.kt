package al.bruno.financaime.data.source.local.dao

import al.bruno.financaime.model.Budget
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import io.reactivex.Single

@Dao
interface BudgetDao {
    @Insert fun insert(budget: Budget) : Single<Long>
    @Update fun updateBudget(budget: Budget) : Single<Long>
    @Update fun updateIncomes(budget: Budget) : Single<Long>
    @Query("SELECT b._id AS _id, b._budget AS _budget, b._incomes AS _incomes, b._date AS _date , SUM(e._expense) AS _expense " +
            "FROM budget AS b LEFT JOIN expense AS e ON b._id = e._id_budget " +
            "WHERE strftime('%m', datetime(b._date/1000, 'unixepoch')) = :month GROUP BY e._id_budget")
    fun budget(month: String) : LiveData<Budget>
}