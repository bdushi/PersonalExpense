package al.bruno.personal.expense.data.source.local

import al.bruno.personal.expense.data.source.SettingsDataSource
import al.bruno.personal.expense.data.source.local.dao.SettingsDao
import al.bruno.personal.expense.model.Settings
import android.content.Context
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SettingsLocalDataSource @Inject constructor(private val settingsDao: SettingsDao): SettingsDataSource {
    override fun insert(settings: Settings): Single<Long> {
        return settingsDao.insert(settings)
    }

    override fun updateIncomes(incomes: Double) {
        return settingsDao.updateIncomes(incomes)
    }

    override fun updateAuto(auto: Boolean) {
        return settingsDao.updateAuto(auto)
    }

    override fun update(settings: Settings): Single<Int> {
        return settingsDao.update(settings)
    }

    override fun delete(settings: Settings): Single<Int> {
        return settingsDao.delete(settings)
    }

    override fun settings(id: Long): Single<Settings> {
        return settingsDao.settings(id)
    }

}
