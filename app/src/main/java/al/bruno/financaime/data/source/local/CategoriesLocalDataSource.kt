package al.bruno.financaime.data.source.local

import al.bruno.financaime.data.source.CategoriesDataSource
import al.bruno.financaime.model.Categories
import android.content.Context
import io.reactivex.Single

class CategoriesLocalDataSource(context: Context) : CategoriesDataSource {
    private var DATABASE_INSTANCE :AppDatabase

    init {
        DATABASE_INSTANCE = AppDatabase.getInstance(context)
    }
    companion object {
        var INSTANCE: CategoriesLocalDataSource? = null

        fun INSTANCE (context: Context) : CategoriesLocalDataSource? {
            if(INSTANCE == null)
                INSTANCE = CategoriesLocalDataSource(context)
            return INSTANCE
        }

    }
    override fun insert(categories: Categories): Single<Long> {
        return DATABASE_INSTANCE.categoriesDao().insert(categories)
    }

    override fun update(categories: Categories): Single<Int> {
        return DATABASE_INSTANCE.categoriesDao().update(categories)
    }

    override fun delete(categories: Categories): Single<Int> {
        return DATABASE_INSTANCE.categoriesDao().delete(categories)
    }

    override fun categories(): Single<List<Categories>> {
        return DATABASE_INSTANCE.categoriesDao().categories()
    }

}