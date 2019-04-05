package al.bruno.personal.expense.data.source

import al.bruno.personal.expense.model.Expense
import androidx.lifecycle.LiveData
import io.reactivex.Completable
import io.reactivex.Single
import org.joda.time.DateTime

interface ExpenseDataSource {
    fun insert(expense: Expense) : Single<Long>
    fun insert(expenses: List<Expense>) : Completable
    fun expense(id: Long) : LiveData<Expense>
    fun expenses(month: String, year: String) : Single<List<Expense>>
    fun expenses(date: DateTime) : Single<List<Expense>>
    fun statistics(month: String, year: String) : Single<List<Expense>>
    fun date() : Single<Array<DateTime>>
    fun total(date: DateTime) : Single<String>
}