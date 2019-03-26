package al.bruno.personal.expense.ui.statistic

import al.bruno.personal.expense.data.source.ExpenseRepository
import al.bruno.personal.expense.model.Expense
import androidx.lifecycle.ViewModel
import io.reactivex.Single
import javax.inject.Inject

class StatisticViewModel @Inject constructor(private val expenseRepository : ExpenseRepository) : ViewModel() {
    fun statistics(month: String, year: String): Single<List<Expense>> {
        return expenseRepository.statistics(month, year)
    }
}