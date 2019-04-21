package al.bruno.personal.expense.data.source

import al.bruno.personal.expense.model.ExpenseDetails
import androidx.lifecycle.LiveData
import io.reactivex.Flowable
import io.reactivex.Single

interface ExpenseDetailsDataSource {
    fun budgetDetails(month: String , year: String) : LiveData<ExpenseDetails>
}