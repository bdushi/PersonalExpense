package al.bruno.personal.expense.view.model

import al.bruno.personal.expense.data.source.ExpenseDetailsDataSource
import al.bruno.personal.expense.model.ExpenseDetails
import androidx.lifecycle.ViewModel
import io.reactivex.Single

class ExpenseDetailsViewModel(private val expenseDetailsDataSource: ExpenseDetailsDataSource): ViewModel(), ExpenseDetailsDataSource {
    override fun expense(): Single<ExpenseDetails> {
        return expenseDetailsDataSource.expense();
    }

}