package al.bruno.personal.expense.data.source.local

import al.bruno.personal.expense.data.source.BudgetDetailsDataSource
import al.bruno.personal.expense.model.ExpenseDetails
import android.content.Context
import io.reactivex.Single

class BudgetDetailsLocalDataSource(context: Context) : BudgetDetailsDataSource {
    private val appDatabase: AppDatabase = AppDatabase.getInstance(context)

    companion object {
        private var INSTANCE: BudgetDetailsDataSource? = null
        fun INSTANCE (context: Context): BudgetDetailsDataSource?  {
            if(INSTANCE == null) {
                INSTANCE = BudgetDetailsLocalDataSource(context = context)
            }
            return INSTANCE
        }
    }

    override fun budgetDetails(month: String, year: String): Single<ExpenseDetails> {
        return appDatabase.budgetDetailsDao().budgetDetails(month, year)
    }
}