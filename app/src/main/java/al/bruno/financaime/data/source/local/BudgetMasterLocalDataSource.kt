package al.bruno.financaime.data.source.local

import al.bruno.financaime.data.source.BudgetMasterDataSource
import al.bruno.financaime.model.BudgetMaster
import io.reactivex.Single

class BudgetMasterLocalDataSource : BudgetMasterDataSource {
    override fun budget(month: String): Single<List<BudgetMaster>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}