package al.bruno.personal.expense.data.source

import al.bruno.personal.expense.data.source.local.dao.ExpenseMasterDao
import al.bruno.personal.expense.model.ExpenseMaster
import androidx.lifecycle.LiveData
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ExpenseMasterRepository @Inject constructor(private var expenseDataSource: ExpenseMasterDataSource) : ExpenseMasterDataSource {
    override fun expenseMaster(month: String, year: String): LiveData<List<ExpenseMaster>> {
        return expenseDataSource.expenseMaster(month, year)
    }
}