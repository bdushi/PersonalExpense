package al.bruno.financaime.dependency.injection

import al.bruno.financaime.data.source.SettingsDataSource
import al.bruno.financaime.data.source.SettingsRepository
import al.bruno.financaime.data.source.local.SettingsLocalDataSource
import android.content.Context
import androidx.annotation.NonNull

object InjectionProvider {
    fun providerSettingsInjection(@NonNull context: Context) : SettingsDataSource? {
        return SettingsRepository.getInstance(SettingsLocalDataSource.getInstance(context)!!)
    }
}