package al.bruno.financaime.data.source.local

import al.bruno.financaime.data.source.BudgetDetailsDataSource
import al.bruno.financaime.model.BudgetDetails
import android.content.Context
import io.reactivex.Single

class BudgetDetailsLocalDataSource(context: Context) : BudgetDetailsDataSource {
    private val appDatabase: AppDatabase = AppDatabase.getInstance(context)

    companion object {
        private var INSTANCE: BudgetDetailsDataSource? = null
        fun newInstance (context: Context): BudgetDetailsDataSource?  {
            if(INSTANCE == null) {
                INSTANCE = BudgetDetailsLocalDataSource(context = context)
            }
            return INSTANCE
        }
    }
    override fun budgetDetails(month: String, year: String): Single<BudgetDetails> {
        return appDatabase.budgetDetailsDao().budgetDetails(month, year)
    }
}