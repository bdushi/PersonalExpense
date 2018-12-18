package al.bruno.personal.expense.data.source

import al.bruno.personal.expense.model.Incomes
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

    override fun insert(incomes: Incomes): Single<Long> {
        return budgetDataSource.insert(incomes)
    }

    /*override fun updateIncomes(incomes: Double, id: Long): Single<Int> {
        return budgetDataSource.updateIncomes(incomes, id)
    }*/

    override fun updateIncomes(incomes: Double, id: Long)/*: Observable<Int>*/ {
        return budgetDataSource.updateIncomes(incomes, id)
    }

    override fun budget(month: String): LiveData<Incomes> {
        return budgetDataSource.budget(month)
    }
    override fun expense(month: String): LiveData<Incomes> {
        return budgetDataSource.expense(month)
    }

}