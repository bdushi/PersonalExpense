package al.bruno.personal.expense.view.model

import al.bruno.personal.expense.dependency.injection.InjectionProvider.provideBudgetInjection
import al.bruno.personal.expense.data.source.BudgetDataSource
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import al.bruno.personal.expense.model.Incomes
import androidx.lifecycle.LiveData
import io.reactivex.Single


class BudgetViewModel(application: Application) : AndroidViewModel(application), BudgetDataSource {
    private val budgetRepository: BudgetDataSource = provideBudgetInjection(application)!!

    override fun budget (month: String) : LiveData<Incomes> {
        return budgetRepository.budget(month);
    }
    override fun expense(month: String): LiveData<Incomes> {
        return budgetRepository.expense(month);
    }

    override fun insert(incomes: Incomes) : Single<Long> {
        return budgetRepository.insert(incomes);
    }

    override fun updateIncomes(incomes: Double, id:Long) {
        return budgetRepository.updateIncomes(incomes, id)
    }
}