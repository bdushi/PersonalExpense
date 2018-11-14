package al.bruno.financaime.view.model

import al.bruno.financaime.data.source.BudgetDetailsDataSource
import al.bruno.financaime.dependency.injection.BudgetDetailsInjection
import al.bruno.financaime.model.BudgetDetails
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import io.reactivex.Single

class BudgetDetailsViewModel(application: Application) : AndroidViewModel(application), BudgetDetailsDataSource {
    private val budgetDetailsDataSource = BudgetDetailsInjection.provideBudgetDetailsInjection(application)
    override fun budgetDetails(month: String, year: String): Single<BudgetDetails> {
        return budgetDetailsDataSource!!.budgetDetails(month, year)
    }
}