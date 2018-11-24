package al.bruno.personal.expense.data.source.local

import al.bruno.personal.expense.data.source.SettingsDataSource
import al.bruno.personal.expense.model.Settings
import android.content.Context
import io.reactivex.Single

class SettingsLocalDataSource(context: Context): SettingsDataSource {
    private val appDataSource = AppDatabase.getInstance(context)
    companion object {
        private var INSTANCE: SettingsDataSource? = null
        fun getInstance (context: Context):SettingsDataSource?  {
            if(INSTANCE == null)
                INSTANCE = SettingsLocalDataSource(context)
            return INSTANCE
        }
    }
    override fun insert(settings: Settings): Single<Long> {
        return appDataSource.settingsDao().insert(settings)
    }

    override fun updateIncomes(incomes: Double) {
        return appDataSource.settingsDao().updateIncomes(incomes)
    }

    override fun updateBudget(budget: Double) {
        return appDataSource.settingsDao().updateBudget(budget)
    }

    override fun updateAuto(auto: Boolean) {
        return appDataSource.settingsDao().updateAuto(auto)
    }

    override fun update(settings: Settings): Single<Int> {
        return appDataSource.settingsDao().update(settings)
    }

    override fun delete(settings: Settings): Single<Int> {
        return appDataSource.settingsDao().delete(settings)
    }

    override fun settings(id: Long): Single<Settings> {
        return appDataSource.settingsDao().settings(id)
    }

}
