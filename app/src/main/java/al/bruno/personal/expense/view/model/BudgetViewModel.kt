package al.bruno.personal.expense.view.model

import al.bruno.financaime.dependency.injection.InjectionProvider.provideBudgetInjection
import al.bruno.personal.expense.data.source.BudgetDataSource
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import al.bruno.personal.expense.model.Budget
import androidx.lifecycle.LiveData
import io.reactivex.Single


class BudgetViewModel(application: Application) : AndroidViewModel(application), BudgetDataSource {
    private val budgetRepository: BudgetDataSource = provideBudgetInjection(application)!!

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