package al.bruno.financaime.view.model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import al.bruno.financaime.data.source.BudgetRepository
import al.bruno.financaime.dependency.injection.BudgetInjection
import al.bruno.financaime.model.Budget
import androidx.lifecycle.LiveData
import io.reactivex.Single


class BudgetViewModel(application: Application) : AndroidViewModel(application) {
    private val budgetRepository: BudgetRepository
    init {
        this.budgetRepository = BudgetInjection.provideBudgetInjection(application)!!;
    }

    fun budget (month: String) : LiveData<Budget> {
        return budgetRepository.budget(month);
    }
    fun insert(budget: Budget) : Single<Long> {
        return budgetRepository.insert(budget);
    }
    fun updateBudget(budget: Double, id:Long) : Single<Long> {
        return budgetRepository.updateBudget(budget, id)
    }
    fun updateIncomes(incomes: Double, id:Long) : Single<Long> {
        return budgetRepository.updateIncomes(incomes, id)
    }
}