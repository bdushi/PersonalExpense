package al.bruno.personal.expense.data.source.local.dao

import al.bruno.personal.expense.model.Incomes
import androidx.lifecycle.LiveData
import androidx.room.*
import io.reactivex.Single

@Dao
interface IncomesDao {
    /*@Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(budget: Incomes) : Single<Long>

    @Query("UPDATE incomes SET _incomes = :incomes WHERE _id = :id")
    fun updateIncomes(incomes: Double, id:Long)//: Observable<Int>

    @Query("SELECT i._id AS _id, i._incomes AS _incomes, i._date AS _date FROM incomes AS i WHERE strftime('%m', datetime(i._date/1000, 'unixepoch')) = :month")
    fun budget(month: String) : LiveData<Incomes>

    @Query("SELECT i._id AS _id, (i._incomes - (SELECT TOTAL(e._amount) FROM expense AS e WHERE e._id_budget = i._id)) AS _budget, i._incomes AS _incomes, i._date AS _date FROM incomes AS i WHERE strftime('%m', datetime(i._date/1000, 'unixepoch')) = :month")
    fun expense(month: String) : LiveData<Incomes>*/
}