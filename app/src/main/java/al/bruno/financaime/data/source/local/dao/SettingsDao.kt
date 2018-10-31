package al.bruno.financaime.data.source.local.dao

import al.bruno.financaime.model.Settings
import androidx.room.*
import io.reactivex.Flowable
import io.reactivex.Single

@Dao
interface SettingsDao {
    @Insert
    fun insert(settings: Settings) : Single<Long>

    @Query("UPDATE settings SET _incomes = :incomes")
    fun updateIncomes(incomes: Double) : Single<Long>
    @Query("UPDATE settings SET _budget = :budget")
    fun updateBudget(budget: Double) : Single<Long>
    @Query("UPDATE settings SET _auto = :auto")
    fun updateAuto(auto: Boolean) : Single<Long>
    @Delete
    fun delete(settings: Settings) : Single<Long>
    @Query("SELECT * FROM settings")
    fun settings() : Flowable<Long>
}