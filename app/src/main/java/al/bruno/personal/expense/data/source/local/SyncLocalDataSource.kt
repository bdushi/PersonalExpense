package al.bruno.personal.expense.data.source.local

import al.bruno.personal.expense.data.source.SyncDataSource
import al.bruno.personal.expense.data.source.local.dao.SyncDao
import al.bruno.personal.expense.sync.Categories
import al.bruno.personal.expense.sync.Expense
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SyncLocalDataSource @Inject constructor(private val syncDao: SyncDao) : SyncDataSource {
    override fun categories(): Single<List<Categories>> {
        return syncDao.categories()
    }

    override fun expenses(): Single<List<Expense>> {
        return syncDao.expenses()
    }

}