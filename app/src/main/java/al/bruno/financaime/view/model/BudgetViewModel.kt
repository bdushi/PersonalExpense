package al.bruno.financaime.view.model

import al.bruno.financaime.data.source.BudgetDataSource
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import al.bruno.financaime.data.source.BudgetRepository
import al.bruno.financaime.dependency.injection.BudgetInjection
import al.bruno.financaime.model.Budget
import androidx.lifecycle.LiveData
import io.reactivex.Single


class BudgetViewModel(application: Application) : AndroidViewModel(application), BudgetDataSource {
    private val budgetRepository: BudgetRepository
    init {
        this.budgetRepository = BudgetInjection.provideBudgetInjection(application)!!;
    }

    override fun budget (month: String) : LiveData<Budget> {
        return budgetRepository.budget(month);
    }
    override fun expense(month: String): LiveData<Budget> {
        return budgetRepository.expense(month);
    }

    override fun insert(budget: Budget) : Single<Long> {
        return budgetRepository.insert(budget);
    }
    override fun updateBudget(budget: Double, id:Long) {
        return budgetRepository.updateBudget(budget, id)
    }
    override fun updateIncomes(incomes: Double, id:Long) {
        return budgetRepository.updateIncomes(incomes, id)
    }
}