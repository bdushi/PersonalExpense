package al.bruno.financaime.data.source.remote

import al.bruno.financaime.data.source.BudgetDataSource
import al.bruno.financaime.model.Budget
import androidx.lifecycle.LiveData
import io.reactivex.Single

public class BudgetLocalDataSource : BudgetDataSource {
    override fun insert(budget: Budget): Single<Long> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun updateBudget(budget: Double, id: Long): Single<Long> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun updateIncomes(incomes: Double, id: Long): Single<Long> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun budget(month: String): LiveData<Budget> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}