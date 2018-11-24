package al.bruno.personal.expense.dependency.injection

import al.bruno.personal.expense.data.source.SettingsDataSource
import al.bruno.personal.expense.data.source.SettingsRepository
import al.bruno.personal.expense.data.source.local.SettingsLocalDataSource
import android.content.Context
import androidx.annotation.NonNull

object InjectionProvider {
    fun providerSettingsInjection(@NonNull context: Context) : SettingsDataSource? {
        return SettingsRepository.getInstance(SettingsLocalDataSource.getInstance(context)!!)
    }
}