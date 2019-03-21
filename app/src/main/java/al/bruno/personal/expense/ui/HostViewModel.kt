package al.bruno.personal.expense.ui

import al.bruno.personal.expense.data.source.CategoriesRepository
import al.bruno.personal.expense.model.Categories
import androidx.lifecycle.ViewModel
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HostViewModel @Inject constructor(private val categoriesRepository: CategoriesRepository) : ViewModel() {
    fun categories(type: String): Single<List<Categories>> {
        return categoriesRepository.categories(type)
    }
}