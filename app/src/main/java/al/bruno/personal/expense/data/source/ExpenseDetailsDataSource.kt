package al.bruno.personal.expense.data.source

import al.bruno.personal.expense.model.ExpenseDetails
import io.reactivex.Single

interface ExpenseDetailsDataSource {
    fun budgetDetails(month: String , year: String) : Single<ExpenseDetails>
}