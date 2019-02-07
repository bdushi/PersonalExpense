package al.bruno.personal.expense.data.source.local

import al.bruno.personal.expense.data.source.BudgetDataSource
import al.bruno.personal.expense.data.source.local.dao.BudgetDao
import al.bruno.personal.expense.model.Budget
import androidx.lifecycle.LiveData
import io.reactivex.Single

class BudgetLocalDataSource(private val budgetDao: BudgetDao) : BudgetDataSource {
    companion object {
        var INSTANCE: BudgetDataSource? = null

        fun getInstance(budgetDao: BudgetDao): BudgetDataSource? {
            if (INSTANCE == null)
                INSTANCE = BudgetLocalDataSource(budgetDao)
            return INSTANCE
        }
    }

    override fun insert(budget: Budget): Single<Long> {
        return budgetDao.insert(budget)
    }

    override fun updateBudget(budget: Double, id: Long)/*: Observable<Int>*/ {
        return budgetDao.updateBudget(budget, id);
    }

    override fun updateIncomes(incomes: Double, id: Long)/*: Observable<Int>*/ {
        return budgetDao.updateIncomes(incomes, id)
    }

    /*override fun updateBudget(budget: Double, id: Long): Single<Int> {
        return budgetDao.updateBudget(budget, id);
    }

    override fun updateIncomes(incomes: Double, id: Long): Single<Int> {
        return budgetDao.updateIncomes(incomes, id)
    }*/

    override fun budget(month: String): LiveData<Budget> {
        return budgetDao.budget(month)
    }
    override fun expense(month: String): LiveData<Budget> {
        return budgetDao.expense(month)
    }
}