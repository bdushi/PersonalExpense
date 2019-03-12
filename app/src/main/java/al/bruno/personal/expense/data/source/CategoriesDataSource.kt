package al.bruno.personal.expense.data.source

import al.bruno.personal.expense.model.Categories
import io.reactivex.Single

interface CategoriesDataSource {
    fun insert(categories: Categories) : Single<Long>
    fun update(categories: Categories) : Single<Int>
    fun delete(categories: Categories) : Single<Int>
    fun categories(type: String) : Single<List<Categories>>
}