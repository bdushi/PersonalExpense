package al.bruno.personal.expense.dialog.edit.expense

import al.bruno.personal.expense.data.source.ExpenseRepository
import al.bruno.personal.expense.model.Expense
import androidx.lifecycle.ViewModel
import io.reactivex.Single
import javax.inject.Inject

class EditExpenseViewModel @Inject constructor(private val expenseRepository: ExpenseRepository) : ViewModel(){
    fun insert(expense: Expense): Single<Long> {
        return expenseRepository.insert(expense)
    }
}
