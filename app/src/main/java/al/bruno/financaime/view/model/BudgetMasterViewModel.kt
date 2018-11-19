package al.bruno.financaime.view.model

import al.bruno.financaime.data.source.BudgetMasterDataSource
import al.bruno.financaime.dependency.injection.BudgetMasterInjection.providerBudgetMasterInjection
import al.bruno.financaime.model.BudgetMaster
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData

class BudgetMasterViewModel(application: Application) : AndroidViewModel(application), BudgetMasterDataSource {
    private var budgetMasterDataSource: BudgetMasterDataSource = providerBudgetMasterInjection(application)!!

    override fun budget(month: String): LiveData<BudgetMaster> {
        return budgetMasterDataSource.budget(month)
    }
}