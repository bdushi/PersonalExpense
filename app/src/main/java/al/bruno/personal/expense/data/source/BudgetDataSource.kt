package al.bruno.personal.expense.data.source

import al.bruno.personal.expense.model.Incomes
import androidx.lifecycle.LiveData
import io.reactivex.Single

interface BudgetDataSource {
    fun insert(incomes: Incomes) : Single<Long>
    fun updateIncomes(incomes: Double, id:Long)/*: Observable<Int>*/
    fun budget(month: String) : LiveData<Incomes>
    fun expense(month: String) : LiveData<Incomes>
}