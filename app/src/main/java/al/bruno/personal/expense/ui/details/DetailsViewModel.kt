package al.bruno.personal.expense.ui.details

import al.bruno.personal.expense.data.source.ExpenseRepository
import al.bruno.personal.expense.model.Expense
import androidx.lifecycle.ViewModel
import io.reactivex.Single
import org.joda.time.DateTime
import javax.inject.Inject

class DetailsViewModel @Inject constructor(private val expenseRepository : ExpenseRepository) : ViewModel() {

    fun date(): Single<Array<DateTime>> {
        return expenseRepository.date()
    }

    fun expenses(date: DateTime): Single<List<Expense>> {
        return expenseRepository.expenses(date)
    }

    fun total(date: DateTime): Single<String> {
        return expenseRepository.total(date)
    }
}