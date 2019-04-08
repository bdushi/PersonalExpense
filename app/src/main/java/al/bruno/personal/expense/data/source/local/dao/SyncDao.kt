package al.bruno.personal.expense.data.source.local.dao

import al.bruno.personal.expense.sync.Expense
import al.bruno.personal.expense.sync.Categories
import androidx.room.Dao
import androidx.room.Query
import io.reactivex.Single

@Dao
interface SyncDao {
    //strftime('%s', 'now') AS
    @Query("SELECT _category, _type, _sync_time FROM categories")
    fun categories() : Single<List<Categories>>

    //strftime('%s', 'now') AS
    @Query("SELECT _category, _type, _memo, _amount, _date, _sync_time FROM expense")
    fun expenses() : Single<List<Expense>>
}
