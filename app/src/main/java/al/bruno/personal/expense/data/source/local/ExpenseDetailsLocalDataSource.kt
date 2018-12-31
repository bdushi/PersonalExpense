package al.bruno.personal.expense.data.source.local

import al.bruno.personal.expense.data.source.ExpenseDetailsDataSource
import al.bruno.personal.expense.model.ExpenseDetails
import android.content.Context
import io.reactivex.Single

class ExpenseDetailsLocalDataSource(context: Context) : ExpenseDetailsDataSource {
    private val appDatabase: AppDatabase = AppDatabase.getInstance(context)

    companion object {
        private var INSTANCE: ExpenseDetailsDataSource? = null
        fun INSTANCE (context: Context): ExpenseDetailsDataSource?  {
            if(INSTANCE == null) {
                INSTANCE = ExpenseDetailsLocalDataSource(context = context)
            }
            return INSTANCE
        }
    }

    override fun budgetDetails(month: String, year: String): Single<ExpenseDetails> {
        return appDatabase.budgetDetailsDao().budgetDetails(month, year)
    }
}