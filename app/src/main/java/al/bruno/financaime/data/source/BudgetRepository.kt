package al.bruno.financaime.data.source

import al.bruno.financaime.model.Budget
import androidx.lifecycle.LiveData
import io.reactivex.Single

class BudgetRepository(budgetDataSource: BudgetDataSource) : BudgetDataSource {
    private val budgetDataSource: BudgetDataSource
    init {
        this.budgetDataSource = budgetDataSource
    }

    companion object {
        private var INSTANCE: BudgetDataSource? = null
        fun getInstance(budgetDataSource: BudgetDataSource) : BudgetDataSource? {
            if(INSTANCE == null)
                INSTANCE = BudgetRepository(budgetDataSource)
            return INSTANCE
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }

    override fun insert(budget: Budget): Single<Long> {
        return budgetDataSource.insert(budget)
    }

    /*override fun updateBudget(budget: Double, id: Long): Single<Int> {
        return budgetDataSource.updateBudget(budget, id)
    }

    override fun updateIncomes(incomes: Double, id: Long): Single<Int> {
        return budgetDataSource.updateIncomes(incomes, id)
    }*/
    override fun updateBudget(budget: Double, id: Long)/*: Observable<Int> */{
        return budgetDataSource.updateBudget(budget, id)
    }

    override fun updateIncomes(incomes: Double, id: Long)/*: Observable<Int>*/ {
        return budgetDataSource.updateIncomes(incomes, id)
    }

    override fun budget(month: String): LiveData<Budget> {
        return budgetDataSource.budget(month)
    }
    override fun expense(month: String): LiveData<Budget> {
        return budgetDataSource.expense(month)
    }

}