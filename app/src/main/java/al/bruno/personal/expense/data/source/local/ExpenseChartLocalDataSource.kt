package al.bruno.personal.expense.data.source.local

import al.bruno.personal.expense.data.source.ExpenseChartDataSource
import al.bruno.personal.expense.data.source.local.dao.ExpenseChartDao
import al.bruno.personal.expense.entities.Chart
import al.bruno.personal.expense.entities.ExpenseChart
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ExpenseChartLocalDataSource @Inject constructor(private val expenseChartDao: ExpenseChartDao): ExpenseChartDataSource {
    override fun expenseChart(month: String, year: String): Single<List<ExpenseChart>> {
        return expenseChartDao.expenseChart(month, year)
    }
    override fun chart(month: String, year: String): Single<List<Chart>> {
        return expenseChartDao.chart()
    }
}