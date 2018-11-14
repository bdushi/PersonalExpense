package al.bruno.financaime.data.source

import al.bruno.financaime.model.Budget
import androidx.lifecycle.LiveData
import io.reactivex.Observable
import io.reactivex.Single

interface BudgetDataSource {
    fun insert(budget: Budget) : Single<Long>
    fun updateBudget(budget: Double, id:Long)/*: Observable<Int>*/
    fun updateIncomes(incomes: Double, id:Long)/*: Observable<Int>*/
    fun budget(month: String) : LiveData<Budget>
    fun expense(month: String) : LiveData<Budget>
}