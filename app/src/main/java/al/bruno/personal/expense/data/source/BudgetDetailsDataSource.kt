package al.bruno.personal.expense.data.source

import al.bruno.personal.expense.model.ExpenseDetails
import io.reactivex.Single

interface BudgetDetailsDataSource {
    fun budgetDetails(month: String , year: String) : Single<ExpenseDetails>
}