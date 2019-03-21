package al.bruno.personal.expense.ui.home

import al.bruno.personal.expense.data.source.HomeRepository
import al.bruno.personal.expense.entities.Chart
import al.bruno.personal.expense.model.ExpenseDetails
import al.bruno.personal.expense.model.ExpenseMaster
import androidx.lifecycle.ViewModel
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HomeViewModel @Inject constructor(private val homeRepository: HomeRepository) : ViewModel() {
    fun chart(month: String, year: String): Single<List<Chart>> {
        return homeRepository.chart()
    }

    fun budgetDetails(month: String, year: String): Single<ExpenseDetails> {
        return homeRepository.budgetDetails(month, year)
    }

    fun expenseMaster(month: String, year: String): Single<List<ExpenseMaster>> {
        return homeRepository.expenseMaster(month = month, year = year)
    }
}
