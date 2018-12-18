package al.bruno.personal.expense.data.source

import al.bruno.personal.expense.model.Settings
import io.reactivex.Single

class SettingsRepository(private val settingsDataSource: SettingsDataSource) : SettingsDataSource {
    companion object {
        private var settingsDataSource: SettingsDataSource? = null
        fun getInstance(mSettingsDataSource: SettingsDataSource): SettingsDataSource? {
            if(settingsDataSource == null)
                settingsDataSource = SettingsRepository(mSettingsDataSource)
            return settingsDataSource
        }

        fun destroyInstance() {
            settingsDataSource = null
        }
    }

    override fun insert(settings: Settings): Single<Long> {
        return settingsDataSource.insert(settings)
    }

    override fun updateIncomes(incomes: Double) {
        return settingsDataSource.updateIncomes(incomes)
    }

    override fun updateAuto(auto: Boolean) {
        return settingsDataSource.updateAuto(auto)
    }

    override fun update(settings: Settings): Single<Int> {
        return settingsDataSource.update(settings)
    }

    override fun delete(settings: Settings): Single<Int> {
        return settingsDataSource.delete(settings)
    }

    override fun settings(id: Long): Single<Settings> {
        return settingsDataSource.settings(id)
    }
}