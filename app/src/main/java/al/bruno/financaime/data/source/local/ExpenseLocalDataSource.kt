package al.bruno.financaime.data.source.local

import al.bruno.financaime.data.source.ExpenseDataSource
import al.bruno.financaime.model.Expense
import android.content.Context
import androidx.lifecycle.LiveData
import io.reactivex.Single
import java.util.*
import kotlin.math.exp

class ExpenseLocalDataSource(context: Context) : ExpenseDataSource {
    private var DATABASE_INSTANCE :AppDatabase
    init {
        DATABASE_INSTANCE = AppDatabase.getInstance(context)
    }

    companion object {
        private var INSTANCE : ExpenseLocalDataSource? = null
        fun INSTANCE(context: Context) : ExpenseLocalDataSource? {
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

    override fun date(): Single<List<Date>> {
        return DATABASE_INSTANCE.expenseDao().date()
    }

}