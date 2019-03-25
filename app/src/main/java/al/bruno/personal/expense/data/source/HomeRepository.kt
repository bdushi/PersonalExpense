package al.bruno.personal.expense.data.source

import al.bruno.personal.expense.data.source.local.dao.HomeDao
import al.bruno.personal.expense.entities.Chart
import al.bruno.personal.expense.model.ExpenseDetails
import al.bruno.personal.expense.model.ExpenseMaster
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HomeRepository @Inject constructor(private val homeDataSource: HomeDataSource) {
    fun chart(): Single<List<Chart>> {
        return homeDataSource.chart();
    }

    fun budgetDetails(month: String, year: String): Single<ExpenseDetails> {
        return homeDataSource.budgetDetails(month, year)
    }

    fun expenseMaster(month: String, year: String): Single<List<ExpenseMaster>> {
        return homeDataSource.expenseMaster(month, year);
    }
}