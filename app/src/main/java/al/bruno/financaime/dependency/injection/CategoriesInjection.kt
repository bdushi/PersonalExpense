package al.bruno.financaime.dependency.injection

import al.bruno.financaime.data.source.CategoriesDataSource
import al.bruno.financaime.data.source.CategoriesRepository
import al.bruno.financaime.data.source.local.CategoriesLocalDataSource
import android.content.Context
import androidx.annotation.NonNull

object CategoriesInjection {
    fun providerCategoriesInjection(@NonNull context: Context) : CategoriesDataSource? {
        return CategoriesRepository.getInstance(CategoriesLocalDataSource.newInstance(context)!!)
    }
}