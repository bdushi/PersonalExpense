package al.bruno.personal.expense.view.model

import al.bruno.personal.expense.data.source.CategoriesDataSource
import al.bruno.personal.expense.dependency.injection.CategoriesInjection
import al.bruno.personal.expense.model.Categories
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import io.reactivex.Single

class CategoriesViewModel(application: Application) : AndroidViewModel(application), CategoriesDataSource {
    private val categoriesDataSource: CategoriesDataSource = CategoriesInjection.providerCategoriesInjection(application)!!

    override fun insert(categories: Categories): Single<Long> {
        return categoriesDataSource.insert(categories)
    }

    override fun update(categories: Categories): Single<Int> {
        return categoriesDataSource.update(categories)
    }

    override fun delete(categories: Categories): Single<Int> {
        return categoriesDataSource.delete(categories)
    }

    override fun categories(): Single<List<Categories>> {
        return categoriesDataSource.categories()
    }
}