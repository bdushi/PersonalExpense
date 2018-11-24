package al.bruno.personal.expense.view.model

import al.bruno.personal.expense.data.source.BudgetMasterDataSource
import al.bruno.personal.expense.dependency.injection.BudgetMasterInjection.providerBudgetMasterInjection
import al.bruno.personal.expense.model.BudgetMaster
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData

class BudgetMasterViewModel(application: Application) : AndroidViewModel(application), BudgetMasterDataSource {
    private var budgetMasterDataSource: BudgetMasterDataSource = providerBudgetMasterInjection(application)!!

    override fun budget(month: String): LiveData<BudgetMaster> {
        return budgetMasterDataSource.budget(month)
    }
}