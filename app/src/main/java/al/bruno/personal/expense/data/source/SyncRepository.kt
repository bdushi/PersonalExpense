package al.bruno.personal.expense.data.source

import al.bruno.personal.expense.sync.Categories
import al.bruno.personal.expense.sync.Expense
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SyncRepository @Inject constructor(private val syncDataSource: SyncDataSource) : SyncDataSource {
    override fun categories(): Single<List<Categories>> {
        return syncDataSource.categories()
    }

    override fun expenses(): Single<List<Expense>> {
        return syncDataSource.expenses()
    }

}