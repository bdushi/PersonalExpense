package al.bruno.personal.expense.data.source.local

import al.bruno.personal.expense.data.source.ExpenseDataSource
import al.bruno.personal.expense.model.Expense
import android.content.Context
import androidx.lifecycle.LiveData
import io.reactivex.Single
import org.joda.time.DateTime

class ExpenseLocalDataSource(context: Context) : ExpenseDataSource {
    private var DATABASE_INSTANCE :AppDatabase = AppDatabase.getInstance(context)
    
    companion object {
        private var INSTANCE : ExpenseDataSource? = null
        fun INSTANCE(context: Context) : ExpenseDataSource? {
            if(INSTANCE == null)
                INSTANCE = ExpenseLocalDataSource(context)
            return INSTANCE
        }
    }
    override fun insert(expense: Expense): Single<Long> {
        return DATABASE_INSTANCE.expenseDao().insert(expense)
    }

    override fun expense(id: Long): LiveData<Expense> {
        return DATABASE_INSTANCE.expenseDao().expense(id)
    }

    override fun expenses(month: String): LiveData<List<Expense>> {
        return DATABASE_INSTANCE.expenseDao().expenses(month)
    }

    override fun expenses(month: String, year: String): LiveData<List<Expense>> {
        return DATABASE_INSTANCE.expenseDao().expenses(month, year)
    }

    override fun date(): Single<List<DateTime>> {
        return DATABASE_INSTANCE.expenseDao().date()
    }
    override fun expenses(date: DateTime): Single<List<Expense>> {
        return DATABASE_INSTANCE.expenseDao().expenses(date)
    }

    override fun total(date: DateTime): Single<String> {
        return DATABASE_INSTANCE.expenseDao().total(date)
    }
}