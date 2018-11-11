package al.bruno.financaime.data.source.local.dao

import al.bruno.financaime.model.Expense
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import io.reactivex.Single
import java.util.*

@Dao
interface ExpenseDao {
    @Insert
    fun insert(expense: Expense) : Single<Long>

    @Query("SELECT * FROM expense WHERE _id = :id")
    fun expense(id: Long) : LiveData<Expense>

    //@Query("SELECT _id, _expense, SUM(_amount) AS _amount, _date FROM expense WHERE strftime('%m', datetime(_date/1000, 'unixepoch')) = :month GROUP BY TRIM(_expense)")
    @Query("SELECT * FROM expense WHERE strftime('%m', datetime(_date/1000, 'unixepoch')) = :month")
    //@Query("SELECT _id, _expense, _amount, _date FROM expense")
    fun expenses(month: String) : LiveData<List<Expense>>

   /* @Query("SELECT * FROM expense " +
            "WHERE strftime('%m',datetime(_date/1000, 'unixepoch')) = :month " +
            "AND strftime('%Y', datetime(_date/1000, 'unixepoch')) = :year " +
            "GROUP BY TRIM(_expense)")*/
    @Query("SELECT * FROM expense WHERE strftime('%m',datetime(_date/1000, 'unixepoch')) = :month AND strftime('%Y', datetime(_date/1000, 'unixepoch')) = :year ")
    fun expenses(month: String, year: String) : LiveData<List<Expense>>

    @Query("SELECT _date FROM expense")
    fun date() : Single<List<Date>>
}