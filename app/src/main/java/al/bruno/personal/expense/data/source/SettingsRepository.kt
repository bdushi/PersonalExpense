package al.bruno.personal.expense.data.source

import al.bruno.personal.expense.model.Settings
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SettingsRepository @Inject constructor(private val settingsDataSource: SettingsDataSource) : SettingsDataSource {
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