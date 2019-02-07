package al.bruno.personal.expense.data.source.local

import al.bruno.personal.expense.data.source.CategoriesDataSource
import al.bruno.personal.expense.data.source.local.dao.CategoriesDao
import al.bruno.personal.expense.model.Categories
import io.reactivex.Single

class CategoriesLocalDataSource(private val categoriesDao: CategoriesDao): CategoriesDataSource {

    companion object {
        private var INSTANCE: CategoriesDataSource? = null
        fun getInstance (categoriesDao: CategoriesDao) : CategoriesDataSource? {
            if(INSTANCE == null)
                INSTANCE = CategoriesLocalDataSource(categoriesDao)
            return INSTANCE
        }
    }
    
    override fun insert(categories: Categories): Single<Long> {
        return categoriesDao.insert(categories)
    }

    override fun update(categories: Categories): Single<Int> {
        return categoriesDao.update(categories)
    }

    override fun delete(categories: Categories): Single<Int> {
        return categoriesDao.delete(categories)
    }

    override fun categories(type: String): Single<List<Categories>> {
        return categoriesDao.categories(type)
    }

}