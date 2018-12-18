package al.bruno.personal.expense.data.source.local

import al.bruno.personal.expense.data.source.BudgetDataSource
import al.bruno.personal.expense.model.Incomes
import android.content.Context
import androidx.annotation.Nullable
import androidx.lifecycle.LiveData
import io.reactivex.Single

class BudgetLocalDataSource : BudgetDataSource {
    private var DATABASE_INSTANCE :AppDatabase

    constructor(context: Context) {
        DATABASE_INSTANCE = AppDatabase.getInstance(context)
    }
    constructor(@Nullable DATABASE_INSTANCE: AppDatabase) {
        this.DATABASE_INSTANCE = DATABASE_INSTANCE
    }
    companion object {
        var INSTANCE: BudgetDataSource? = null

        fun INSTANCE(DATABASE_INSTANCE: AppDatabase): BudgetDataSource? {
            if (INSTANCE == null)
                INSTANCE = BudgetLocalDataSource(DATABASE_INSTANCE)
            return INSTANCE
        }

        fun INSTANCE(context: Context): BudgetDataSource? {
            if (INSTANCE == null)
                INSTANCE = BudgetLocalDataSource(context)
            return INSTANCE
        }
    }

    override fun insert(incomes: Incomes): Single<Long> {
        return DATABASE_INSTANCE.budgetDao().insert(incomes)
    }

    override fun updateIncomes(incomes: Double, id: Long)/*: Observable<Int>*/ {
        return DATABASE_INSTANCE.budgetDao().updateIncomes(incomes, id)
    }

    /*override fun updateIncomes(incomes: Double, id: Long): Single<Int> {
        return DATABASE_INSTANCE.budgetDao().updateIncomes(incomes, id)
    }*/

    override fun budget(month: String): LiveData<Incomes> {
        return DATABASE_INSTANCE.budgetDao().budget(month)
    }
    override fun expense(month: String): LiveData<Incomes> {
        return DATABASE_INSTANCE.budgetDao().expense(month)
    }
}