package al.bruno.personal.expense.data.source

import al.bruno.personal.expense.entities.ExpenseChart
import io.reactivex.Single

class ExpenseChartRepository(private var expenseChartDataSource: ExpenseChartDataSource): ExpenseChartDataSource {
    companion object {
        private var INSTANCE: ExpenseChartDataSource? = null
        fun getInstance (expenseChartDataSource: ExpenseChartDataSource) : ExpenseChartDataSource? {
            if(INSTANCE == null)
                INSTANCE = ExpenseChartRepository(expenseChartDataSource)
            return INSTANCE
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
    override fun expenseChart(month: String, year: String): Single<List<ExpenseChart>> {
        return expenseChartDataSource.expenseChart(month, year)
    }
}