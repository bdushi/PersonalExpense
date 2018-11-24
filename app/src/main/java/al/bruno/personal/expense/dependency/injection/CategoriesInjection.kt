package al.bruno.personal.expense.dependency.injection

import al.bruno.personal.expense.data.source.CategoriesDataSource
import al.bruno.personal.expense.data.source.CategoriesRepository
import al.bruno.personal.expense.data.source.local.CategoriesLocalDataSource
import android.content.Context
import androidx.annotation.NonNull

object CategoriesInjection {
    fun providerCategoriesInjection(@NonNull context: Context) : CategoriesDataSource? {
        return CategoriesRepository.getInstance(CategoriesLocalDataSource.newInstance(context)!!)
    }
}