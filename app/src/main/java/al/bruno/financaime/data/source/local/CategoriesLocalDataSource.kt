package al.bruno.financaime.data.source.local

import al.bruno.financaime.data.source.CategoriesDataSource
import al.bruno.financaime.model.Categories
import android.content.Context
import androidx.lifecycle.LiveData
import io.reactivex.Single

class CategoriesLocalDataSource(context: Context): CategoriesDataSource {
    private var appDatabase :AppDatabase = AppDatabase.getInstance(context)
    companion object {
        var INSTANCE: CategoriesDataSource? = null
        fun newInstance (context: Context) : CategoriesDataSource? {
            if(INSTANCE == null)
                INSTANCE = CategoriesLocalDataSource(context)
            return INSTANCE
        }
    }
    
    override fun insert(categories: Categories): Single<Long> {
        return appDatabase.categoriesDao().insert(categories)
    }

    override fun update(categories: Categories): Single<Int> {
        return appDatabase.categoriesDao().update(categories)
    }

    override fun delete(categories: Categories): Single<Int> {
        return appDatabase.categoriesDao().delete(categories)
    }

    override fun categories(): Single<List<Categories>> {
        return appDatabase.categoriesDao().categories()
    }

}