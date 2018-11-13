package al.bruno.financaime.view.model

import al.bruno.financaime.data.source.CategoriesDataSource
import al.bruno.financaime.data.source.CategoriesRepository
import al.bruno.financaime.dependency.injection.CategoriesInjection
import al.bruno.financaime.model.Categories
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import io.reactivex.Single

class CategoriesViewModel(application: Application) : AndroidViewModel(application), CategoriesDataSource {
    private val categoriesRepository: CategoriesDataSource
    init {
        categoriesRepository = CategoriesInjection.providerCategoriesInjection(application)!!
    }

    override fun insert(categories: Categories): Single<Long> {
        return categoriesRepository.insert(categories)
    }

    override fun update(categories: Categories): Single<Int> {
        return categoriesRepository.update(categories)
    }

    override fun delete(categories: Categories): Single<Int> {
        return categoriesRepository.delete(categories)
    }

    override fun categories(): Single<List<Categories>> {
        return categoriesRepository.categories()
    }
}