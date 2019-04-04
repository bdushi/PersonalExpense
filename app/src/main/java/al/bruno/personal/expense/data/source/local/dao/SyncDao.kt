package al.bruno.personal.expense.data.source.local.dao

import al.bruno.personal.expense.sync.Expense
import al.bruno.personal.expense.sync.Categories
import androidx.room.Dao
import androidx.room.Query
import io.reactivex.Single

@Dao
interface SyncDao {
    @Query("SELECT * FROM categories")
    fun categories() : Single<List<Categories>>

    @Query("SELECT * FROM expense")
    fun expenses() : Single<List<Expense>>
}
