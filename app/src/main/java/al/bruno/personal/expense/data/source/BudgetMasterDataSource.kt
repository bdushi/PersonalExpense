package al.bruno.personal.expense.data.source

import al.bruno.personal.expense.model.BudgetMaster
import androidx.lifecycle.LiveData

interface BudgetMasterDataSource {
    fun budget(month: String) : LiveData<BudgetMaster>
}