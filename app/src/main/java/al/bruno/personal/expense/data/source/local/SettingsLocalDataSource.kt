package al.bruno.personal.expense.data.source.local

import al.bruno.personal.expense.data.source.SettingsDataSource
import al.bruno.personal.expense.data.source.local.dao.SettingsDao
import al.bruno.personal.expense.model.Settings
import io.reactivex.Single

class SettingsLocalDataSource(private val settingsDao: SettingsDao): SettingsDataSource {
    companion object {
        private var INSTANCE: SettingsDataSource? = null
        fun getInstance (settingsDao: SettingsDao):SettingsDataSource?  {
            if(INSTANCE == null)
                INSTANCE = SettingsLocalDataSource(settingsDao)
            return INSTANCE
        }
    }
    override fun insert(settings: Settings): Single<Long> {
        return settingsDao.insert(settings)
    }

    override fun updateIncomes(incomes: Double) {
        return settingsDao.updateIncomes(incomes)
    }

    override fun updateBudget(budget: Double) {
        return settingsDao.updateBudget(budget)
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
