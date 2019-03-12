package al.bruno.personal.expense.view.model

import al.bruno.personal.expense.dependency.injection.InjectionProvider.providerCategoriesInjection
import al.bruno.personal.expense.data.source.CategoriesDataSource
import al.bruno.personal.expense.data.source.local.AppDatabase
import al.bruno.personal.expense.model.Categories
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import io.reactivex.Single

class CategoriesViewModel(application: Application) : AndroidViewModel(application), CategoriesDataSource {
    private val categoriesDataSource: CategoriesDataSource = providerCategoriesInjection(AppDatabase.getInstance(application))!!

    override fun insert(categories: Categories): Single<Long> {
        return categoriesDataSource.insert(categories)
    }

    override fun update(categories: Categories): Single<Int> {
        return categoriesDataSource.update(categories)
    }

    override fun delete(categories: Categories): Single<Int> {
        return categoriesDataSource.delete(categories)
    }

    override fun categories(type: String): Single<List<Categories>> {
        return categoriesDataSource.categories(type)
    }
}