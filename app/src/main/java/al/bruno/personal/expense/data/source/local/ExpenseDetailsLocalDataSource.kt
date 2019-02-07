package al.bruno.personal.expense.data.source.local

import al.bruno.personal.expense.data.source.ExpenseDetailsDataSource
import al.bruno.personal.expense.data.source.local.dao.ExpenseDetailsDao
import al.bruno.personal.expense.model.ExpenseDetails
import io.reactivex.Single

class ExpenseDetailsLocalDataSource(private val expenseDetailsDao: ExpenseDetailsDao): ExpenseDetailsDataSource {
    companion object {
        private var INSTANCE: ExpenseDetailsDataSource? = null
        fun getInstance(expenseDetailsDao: ExpenseDetailsDao) : ExpenseDetailsDataSource? {
            if(INSTANCE == null)
                INSTANCE = ExpenseDetailsLocalDataSource(expenseDetailsDao)
            return INSTANCE
        }
    }

    override fun expense(): Single<ExpenseDetails> {
        return expenseDetailsDao.expense()
    }
}