package al.bruno.personal.expense.data.source.local

import al.bruno.personal.expense.data.source.ExpenseMasterDataSource
import al.bruno.personal.expense.data.source.local.dao.ExpenseMasterDao
import al.bruno.personal.expense.model.ExpenseMaster
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ExpenseMasterLocalDataSource @Inject constructor(private val expenseMasterDao: ExpenseMasterDao): ExpenseMasterDataSource {
    override fun expenseMaster(month: String, year: String): Single<List<ExpenseMaster>> {
        return expenseMasterDao.expenseMaster(month, year)
    }
}