package al.bruno.personal.expense.view.model

import al.bruno.personal.expense.data.source.SettingsDataSource
import al.bruno.personal.expense.model.Settings
import androidx.lifecycle.ViewModel
import io.reactivex.Single

class SettingsViewModel(private val settingsDataSource: SettingsDataSource): ViewModel(), SettingsDataSource {

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
