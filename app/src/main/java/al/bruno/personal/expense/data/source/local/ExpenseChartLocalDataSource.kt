package al.bruno.personal.expense.data.source.local

import al.bruno.personal.expense.data.source.ExpenseChartDataSource
import al.bruno.personal.expense.data.source.local.dao.ExpenseChartDao
import al.bruno.personal.expense.entities.ExpenseChart
import io.reactivex.Single

class ExpenseChartLocalDataSource(private val expenseChartDao: ExpenseChartDao): ExpenseChartDataSource {
    companion object {
        private var INSTANCE: ExpenseChartDataSource? = null
        fun getInstance (expenseChartDao: ExpenseChartDao) : ExpenseChartDataSource? {
            if(INSTANCE == null)
                INSTANCE = ExpenseChartLocalDataSource(expenseChartDao)
            return INSTANCE
        }
    }
    override fun expenseChart(month: String, year: String): Single<List<ExpenseChart>> {
        return expenseChartDao.expenseChart(month, year)
    }
}