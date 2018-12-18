package al.bruno.personal.expense.data.source.remote

import al.bruno.personal.expense.data.source.BudgetDataSource
import al.bruno.personal.expense.model.Incomes
import androidx.lifecycle.LiveData
import io.reactivex.Single

public class BudgetRemoteDataSource : BudgetDataSource {
    override fun expense(month: String): LiveData<Incomes> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun updateIncomes(incomes: Double, id: Long)/*: Observable<Int>*/ {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun insert(incomes: Incomes): Single<Long> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun budget(month: String): LiveData<Incomes> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}