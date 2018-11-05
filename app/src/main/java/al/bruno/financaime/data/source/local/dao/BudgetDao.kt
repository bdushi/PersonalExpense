package al.bruno.financaime.data.source.local.dao

import al.bruno.financaime.model.Budget
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface BudgetDao {
    @Insert
    fun insert(budget: Budget) : Single<Long>

    @Query("UPDATE budget SET _budget = :budget WHERE _id = :id")
    fun updateBudget(budget: Double, id:Long) //: Completable//Single<Int>

    @Query("UPDATE budget SET _incomes = :incomes WHERE _id = :id")
    fun updateIncomes(incomes: Double, id:Long) //: Completable //Single<Int>

    @Query("SELECT b._id AS _id, b._budget - IFNULL((SELECT TOTAL(e._value) FROM expense AS e WHERE e._id_budget = b._id GROUP BY e._id_budget), 0) AS _budget, b._incomes AS _incomes, b._date AS _date FROM budget AS b WHERE strftime('%m', datetime(b._date/1000, 'unixepoch')) = :month")
    fun budget(month: String) : LiveData<Budget>
}