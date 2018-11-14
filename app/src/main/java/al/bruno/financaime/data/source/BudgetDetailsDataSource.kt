package al.bruno.financaime.data.source

import al.bruno.financaime.model.BudgetDetails
import io.reactivex.Single

interface BudgetDetailsDataSource {
    fun budgetDetails(month: String , year: String) : Single<BudgetDetails>
}