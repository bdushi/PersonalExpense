package al.bruno.personal.expense.data.source

import al.bruno.personal.expense.entities.ExpenseChart
import io.reactivex.Single

interface ExpenseChartDataSource {
    fun expenseChart(month: String, year: String): Single<List<ExpenseChart>>
}
