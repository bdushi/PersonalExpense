package al.bruno.personal.expense.data.source

import al.bruno.personal.expense.data.source.local.dao.ExpenseChartDao
import al.bruno.personal.expense.entities.Chart
import al.bruno.personal.expense.entities.ExpenseChart
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ExpenseChartRepository @Inject constructor(private var expenseChartDataSource: ExpenseChartDataSource): ExpenseChartDataSource {
    override fun expenseChart(month: String, year: String): Single<List<ExpenseChart>> {
        return expenseChartDataSource.expenseChart(month, year)
    }
    override fun chart(month: String, year: String): Single<List<Chart>> {
        return expenseChartDataSource.chart(month, year)
    }

}