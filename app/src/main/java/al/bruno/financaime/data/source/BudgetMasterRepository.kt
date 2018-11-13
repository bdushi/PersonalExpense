package al.bruno.financaime.data.source

import al.bruno.financaime.model.BudgetMaster
import io.reactivex.Single

class BudgetMasterRepository : BudgetMasterDataSource {
    override fun budget(month: String): Single<List<BudgetMaster>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}