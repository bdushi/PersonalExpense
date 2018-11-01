package al.bruno.financaime.data.source

import al.bruno.financaime.model.Budget
import androidx.lifecycle.LiveData
import androidx.room.Insert
import androidx.room.Query
import io.reactivex.Completable
import io.reactivex.Single

interface BudgetDataSource {
    fun insert(budget: Budget) : Single<Long>
    fun updateBudget(budget: Double, id:Long) //: Completable//Single<Int>
    fun updateIncomes(incomes: Double, id:Long) //: Completable//Single<Int>
    fun budget(month: String) : LiveData<Budget>
}