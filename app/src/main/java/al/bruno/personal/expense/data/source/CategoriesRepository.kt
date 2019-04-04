package al.bruno.personal.expense.data.source

import al.bruno.personal.expense.model.Categories
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CategoriesRepository @Inject constructor(private var categoriesDataSource: CategoriesDataSource) : CategoriesDataSource {

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