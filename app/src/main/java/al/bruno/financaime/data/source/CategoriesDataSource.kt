package al.bruno.financaime.data.source

import al.bruno.financaime.model.Categories
import io.reactivex.Single

interface CategoriesDataSource {
    fun insert(categories: Categories) : Single<Long>
    fun update(categories: Categories) : Single<Int>
    fun delete(categories: Categories) : Single<Int>
    fun categories() : Single<List<Categories>>
}