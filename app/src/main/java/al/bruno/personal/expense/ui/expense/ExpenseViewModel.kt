package al.bruno.personal.expense.ui.expense

import al.bruno.personal.expense.data.source.CategoriesRepository
import al.bruno.personal.expense.model.Categories
import androidx.lifecycle.ViewModel
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ExpenseViewModel @Inject constructor(private val categoriesRepository: CategoriesRepository) : ViewModel() {
    fun insert(categories: Categories): Single<Long> {
        return categoriesRepository.insert(categories)
    }
    fun categories(type: String): Single<List<Categories>> {
        return categoriesRepository.categories(type)
    }

    fun delete(categories: Categories): Single<Int> {
        return categoriesRepository.delete(categories)
    }
}