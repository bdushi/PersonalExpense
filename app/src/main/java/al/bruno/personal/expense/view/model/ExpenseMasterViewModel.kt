package al.bruno.personal.expense.view.model

import al.bruno.personal.expense.data.source.ExpenseMasterDataSource
import al.bruno.personal.expense.model.ExpenseMaster
import androidx.lifecycle.ViewModel
import io.reactivex.Single

class ExpenseMasterViewModel(val expenseMasterDataSource: ExpenseMasterDataSource) : ViewModel(), ExpenseMasterDataSource {
    override fun expenseMaster(month: String, year: String): Single<List<ExpenseMaster>> {
        return expenseMasterDataSource.expenseMaster(month = month, year = year)
    }
}
