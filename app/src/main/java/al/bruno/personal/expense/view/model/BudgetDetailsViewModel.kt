package al.bruno.personal.expense.view.model

import al.bruno.personal.expense.data.source.BudgetDetailsDataSource
import al.bruno.personal.expense.model.BudgetDetails
import androidx.lifecycle.ViewModel
import io.reactivex.Single

class BudgetDetailsViewModel(val budgetDetailsDataSource: BudgetDetailsDataSource) : ViewModel(), BudgetDetailsDataSource {
    override fun budgetDetails(month: String, year: String): Single<BudgetDetails> {
        return budgetDetailsDataSource.budgetDetails(month, year)
    }

    override fun onCleared() {
        super.onCleared()
    }
}