package al.bruno.personal.expense.view.model

import al.bruno.personal.expense.data.source.ExpenseDetailsDataSource
import al.bruno.personal.expense.model.ExpenseDetails
import androidx.lifecycle.ViewModel
import io.reactivex.Single

class ExpenseDetailsViewModel(val expenseDetailsDataSource: ExpenseDetailsDataSource) : ViewModel(), ExpenseDetailsDataSource {
    override fun budgetDetails(month: String, year: String): Single<ExpenseDetails> {
        return expenseDetailsDataSource.budgetDetails(month, year)
    }

    override fun onCleared() {
        super.onCleared()
    }
}