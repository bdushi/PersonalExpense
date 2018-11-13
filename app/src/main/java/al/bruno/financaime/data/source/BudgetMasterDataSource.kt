package al.bruno.financaime.data.source

import al.bruno.financaime.model.BudgetMaster
import io.reactivex.Single

interface BudgetMasterDataSource {
    fun budget(month: String) : Single<List<BudgetMaster>>
}