package al.bruno.personal.expense.data.source.local.dao

import al.bruno.personal.expense.model.Settings
import androidx.room.*
import io.reactivex.Single

@Dao
interface SettingsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(settings: Settings) : Single<Long>

    @Query("UPDATE settings SET _incomes = :incomes")
    fun updateIncomes(incomes: Double)

    @Query("UPDATE settings SET _auto = :auto")
    fun updateAuto(auto: Boolean)

    @Update
    fun update(settings: Settings): Single<Int>

    @Delete
    fun delete(settings: Settings) : Single<Int>

    @Query("SELECT * FROM settings WHERE _id = :id LIMIT 1")
    fun settings(id: Long): Single<Settings>
}