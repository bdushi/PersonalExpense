package al.bruno.personal.expense.data.source

import al.bruno.personal.expense.entities.Chart
import al.bruno.personal.expense.model.ExpenseDetails
import al.bruno.personal.expense.model.ExpenseMaster
import io.reactivex.Single

interface HomeDataSource {
    fun chart(): Single<List<Chart>>
    fun budgetDetails(month: String , year: String) : Single<ExpenseDetails>
    fun expenseMaster(month: String, year: String): Single<List<ExpenseMaster>>
}
