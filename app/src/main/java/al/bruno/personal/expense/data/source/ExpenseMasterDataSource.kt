package al.bruno.personal.expense.data.source

import al.bruno.personal.expense.model.ExpenseMaster
import androidx.lifecycle.LiveData
import io.reactivex.Single

interface ExpenseMasterDataSource {
    fun expenseMaster(month: String, year: String): LiveData<List<ExpenseMaster>>
}