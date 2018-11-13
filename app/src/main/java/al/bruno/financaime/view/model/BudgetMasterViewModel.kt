package al.bruno.financaime.view.model

import al.bruno.financaime.data.source.BudgetMasterDataSource
import al.bruno.financaime.dependency.injection.BudgetMasterInjection
import al.bruno.financaime.model.BudgetMaster
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import io.reactivex.Single

class BudgetMasterViewModel(application: Application) : AndroidViewModel(application), BudgetMasterDataSource {
    private var budgetMasterDataSource: BudgetMasterDataSource? = null
    init {
        budgetMasterDataSource = BudgetMasterInjection.providerBudgetMasterInjection(application)!!
    }
    override fun budget(month: String): Single<BudgetMaster> {
        return budgetMasterDataSource!!.budget(month)
    }
}