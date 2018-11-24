package al.bruno.personal.expense.data.source

import al.bruno.personal.expense.model.Categories
import io.reactivex.Single

class CategoriesRepository(categoriesDataSource: CategoriesDataSource) : CategoriesDataSource {
    private var categoriesDataSource: CategoriesDataSource

    init {
        this.categoriesDataSource = categoriesDataSource
    }

    companion object {
        private var INSTANCE : CategoriesDataSource? = null
        fun getInstance(categoriesDataSource: CategoriesDataSource) : CategoriesDataSource? {
            if(INSTANCE == null)
                INSTANCE = CategoriesRepository(categoriesDataSource)
            return INSTANCE
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
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