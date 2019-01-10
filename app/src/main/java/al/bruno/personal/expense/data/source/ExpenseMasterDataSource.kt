package al.bruno.personal.expense.data.source

import al.bruno.personal.expense.model.ExpenseMaster
import io.reactivex.Single

interface ExpenseMasterDataSource {
    fun expenseMaster(): Single<List<ExpenseMaster>>
}