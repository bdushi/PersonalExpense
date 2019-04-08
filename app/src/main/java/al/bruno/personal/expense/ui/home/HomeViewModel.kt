package al.bruno.personal.expense.ui.home

import al.bruno.personal.expense.data.source.ExpenseChartRepository
import al.bruno.personal.expense.data.source.ExpenseDetailsRepository
import al.bruno.personal.expense.data.source.ExpenseMasterRepository
import al.bruno.personal.expense.entities.Chart
import al.bruno.personal.expense.model.ExpenseDetails
import al.bruno.personal.expense.model.ExpenseMaster
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import io.reactivex.Flowable
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HomeViewModel @Inject constructor(private val expenseChartRepository: ExpenseChartRepository,  private val expenseDetailsRepository: ExpenseDetailsRepository, private val expenseMasterRepository: ExpenseMasterRepository) : ViewModel() {
    fun chart(month: String, year: String): LiveData<List<Chart>> {
        return expenseChartRepository.chart(month = month, year = year)
    }

    fun budgetDetails(month: String, year: String): LiveData<ExpenseDetails> {
        return expenseDetailsRepository.budgetDetails(month = month, year = year)
    }

    fun expenseMaster(month: String, year: String): LiveData<List<ExpenseMaster>> {
        return expenseMasterRepository.expenseMaster(month = month, year = year)
    }
}
