package al.bruno.personal.expense.data.source.local.dao

import al.bruno.personal.expense.model.Budget
import androidx.lifecycle.LiveData
import androidx.room.*
import io.reactivex.Single

@Dao
interface BudgetDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(budget: Budget) : Single<Long>

    @Query("UPDATE budget SET _budget = :budget WHERE _id = :id")
    fun updateBudget(budget: Double, id:Long)//: Observable<Int>

    @Query("UPDATE budget SET _incomes = :incomes WHERE _id = :id")
    fun updateIncomes(incomes: Double, id:Long)//: Observable<Int>

    @Query("SELECT b._id AS _id, b._budget AS _budget, b._incomes AS _incomes, b._date AS _date FROM budget AS b WHERE strftime('%m', datetime(b._date/1000, 'unixepoch')) = :month")
    fun budget(month: String) : LiveData<Budget>

    @Query("SELECT b._id AS _id, (b._budget - (SELECT TOTAL(e._amount) FROM expense AS e WHERE e._id_budget = b._id)) AS _budget, b._incomes AS _incomes, b._date AS _date FROM budget AS b WHERE strftime('%m', datetime(b._date/1000, 'unixepoch')) = :month")
    fun expense(month: String) : LiveData<Budget>
}