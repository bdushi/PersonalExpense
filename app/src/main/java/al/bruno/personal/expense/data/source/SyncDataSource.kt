package al.bruno.personal.expense.data.source

import al.bruno.personal.expense.sync.Categories
import al.bruno.personal.expense.sync.Expense
import io.reactivex.Single

interface SyncDataSource {
    fun categories() : Single<List<Categories>>
    fun expenses() : Single<List<Expense>>
}