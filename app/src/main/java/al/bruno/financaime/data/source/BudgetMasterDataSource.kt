package al.bruno.financaime.data.source

import al.bruno.financaime.model.BudgetMaster
import androidx.lifecycle.LiveData

interface BudgetMasterDataSource {
    fun budget(month: String) : LiveData<BudgetMaster>
}