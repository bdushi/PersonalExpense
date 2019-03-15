package al.bruno.personal.expense.view.model

import al.bruno.personal.expense.data.source.ExpenseChartDataSource
import al.bruno.personal.expense.entities.Chart
import al.bruno.personal.expense.entities.ExpenseChart
import androidx.lifecycle.ViewModel
import io.reactivex.Single

class ExpenseChartViewModel(private val expenseChartDataSource: ExpenseChartDataSource) : ViewModel(), ExpenseChartDataSource {
    override fun expenseChart(month: String, year: String): Single<List<ExpenseChart>> {
        return expenseChartDataSource.expenseChart(month, year)
    }
    override fun chart(month: String, year: String): Single<List<Chart>> {
        return expenseChartDataSource.chart(month, year)
    }

}
