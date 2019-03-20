package al.bruno.personal.expense.data.source.local

import al.bruno.personal.expense.data.source.ExpenseMasterDataSource
import al.bruno.personal.expense.data.source.local.dao.ExpenseMasterDao
import al.bruno.personal.expense.model.ExpenseMaster
import io.reactivex.Single

class ExpenseMasterLocalDataSource(private val expenseMasterDao: ExpenseMasterDao): ExpenseMasterDataSource {
    companion object {
        private var INSTANCE : ExpenseMasterDataSource? = null
        fun getInstance(expenseMasterDao: ExpenseMasterDao) : ExpenseMasterDataSource? {
            if(INSTANCE == null)
                INSTANCE = ExpenseMasterLocalDataSource(expenseMasterDao)
            return INSTANCE
        }
    }
    override fun expenseMaster(month: String, year: String): Single<List<ExpenseMaster>> {
        return expenseMasterDao.expenseMaster(month, year)
    }
}