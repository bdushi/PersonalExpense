package al.bruno.personal.expense.data.source.local

import al.bruno.personal.expense.data.source.HomeDataSource
import al.bruno.personal.expense.data.source.local.dao.HomeDao
import al.bruno.personal.expense.entities.Chart
import al.bruno.personal.expense.model.ExpenseDetails
import al.bruno.personal.expense.model.ExpenseMaster
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HomeLocalDataSource @Inject constructor(private val homeDao: HomeDao): HomeDataSource {
    override fun chart(): Single<List<Chart>> {
        return homeDao.chart();
    }

    override fun budgetDetails(month: String, year: String): Single<ExpenseDetails> {
        return homeDao.budgetDetails(month, year)
    }

    override fun expenseMaster(month: String, year: String): Single<List<ExpenseMaster>> {
        return homeDao.expenseMaster(month, year);
    }
}