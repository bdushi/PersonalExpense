package al.bruno.personal.expense.data.source

import al.bruno.personal.expense.model.Expense
import androidx.lifecycle.LiveData
import io.reactivex.Single
import java.util.*

interface ExpenseDataSource {
    fun insert(expense: Expense) : Single<Long>
    fun expense(id: Long) : LiveData<Expense>
    fun expenses(month: String) : LiveData<List<Expense>>
    fun expenses(month: String, year: String) : LiveData<List<Expense>>
    fun expenses(date: Date) : Single<List<Expense>>
    fun date() : Single<List<Date>>
    fun total(date: Date) : Single<String>
}