package al.bruno.financaime.data.source

import al.bruno.financaime.model.Settings
import io.reactivex.Flowable
import io.reactivex.Single

interface SettingsDataSource {
    fun insert(settings: Settings) : Single<Long>
    fun updateIncomes(incomes: Double)
    fun updateBudget(budget: Double)
    fun updateAuto(auto: Boolean)
    fun update(settings: Settings): Single<Int>
    fun delete(settings: Settings) : Single<Int>
    fun settings(id: Long): Single<Settings>
}