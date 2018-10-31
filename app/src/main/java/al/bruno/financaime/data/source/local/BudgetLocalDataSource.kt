package al.bruno.financaime.data.source.local

import al.bruno.financaime.data.source.BudgetDataSource
import android.content.Context
import androidx.annotation.Nullable

class BudgetLocalDataSource : BudgetDataSource {

    private var DATABASE_INSTANCE :AppDatabase

    constructor(context: Context) {
        DATABASE_INSTANCE = AppDatabase.getInstance(context)
    }
    constructor(@Nullable DATABASE_INSTANCE: AppDatabase) {
        this.DATABASE_INSTANCE = DATABASE_INSTANCE
    }
    companion object {
        var INSTANCE: BudgetLocalDataSource? = null
        fun INSTANCE(DATABASE_INSTANCE: AppDatabase): BudgetLocalDataSource? {
            if (INSTANCE == null)
                INSTANCE = BudgetLocalDataSource(DATABASE_INSTANCE)
            return INSTANCE
        }
        fun INSTANCE(context: Context): BudgetLocalDataSource? {
            if (INSTANCE == null)
                INSTANCE = BudgetLocalDataSource(context)
            return INSTANCE
        }
    }
}