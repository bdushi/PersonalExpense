package al.bruno.financaime.view.model

import al.bruno.financaime.data.source.BudgetDetailsDataSource
import al.bruno.financaime.model.BudgetDetails
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