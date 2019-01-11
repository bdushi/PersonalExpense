package al.bruno.personal.expense.data.source.local

import al.bruno.personal.expense.data.source.ExpenseChartDataSource
import al.bruno.personal.expense.entities.ExpenseChart
import android.content.Context
import io.reactivex.Single

class ExpenseChartLocalDataSource(context: Context): ExpenseChartDataSource {
    private var appDatabase :AppDatabase = AppDatabase.getInstance(context)
    companion object {
        private var INSTANCE: ExpenseChartDataSource? = null
        fun INSTANCE (context: Context) : ExpenseChartDataSource? {
            if(INSTANCE == null)
                INSTANCE = ExpenseChartLocalDataSource(context)
            return INSTANCE
        }
    }
    override fun expenseChart(month: String, year: String): Single<List<ExpenseChart>> {
        return appDatabase.expenseChartDao().expenseChart(month, year)
    }
}