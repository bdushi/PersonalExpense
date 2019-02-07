package al.bruno.personal.expense.data.source.local

import al.bruno.personal.expense.data.source.BudgetDetailsDataSource
import al.bruno.personal.expense.data.source.local.dao.BudgetDetailsDao
import al.bruno.personal.expense.model.BudgetDetails
import io.reactivex.Single

class BudgetDetailsLocalDataSource(private val budgetDetailsDao: BudgetDetailsDao) : BudgetDetailsDataSource {
    companion object {
        private var INSTANCE: BudgetDetailsDataSource? = null
        fun getInstance (budgetDetailsDao: BudgetDetailsDao): BudgetDetailsDataSource?  {
            if(INSTANCE == null) {
                INSTANCE = BudgetDetailsLocalDataSource(budgetDetailsDao)
            }
            return INSTANCE
        }
    }

    override fun budgetDetails(month: String, year: String): Single<BudgetDetails> {
        return budgetDetailsDao.budgetDetails(month, year)
    }
}