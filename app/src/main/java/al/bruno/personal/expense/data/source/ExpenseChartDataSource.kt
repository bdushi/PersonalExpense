package al.bruno.personal.expense.data.source

import al.bruno.personal.expense.entities.Chart
import al.bruno.personal.expense.entities.ExpenseChart
import androidx.lifecycle.LiveData
import io.reactivex.Single

interface ExpenseChartDataSource {
    fun expenseChart(month: String, year: String): Single<List<ExpenseChart>>
    fun chart(month: String, year: String): LiveData<List<Chart>>
}
