package al.bruno.personal.expense.data.source.local

import al.bruno.personal.expense.data.source.ExpenseMasterDataSource
import al.bruno.personal.expense.model.ExpenseMaster
import android.content.Context
import io.reactivex.Single

class ExpenseMasterLocalDataSource(context: Context): ExpenseMasterDataSource {
    private var DATABASE_INSTANCE :AppDatabase = AppDatabase.getInstance(context)

    companion object {
        private var INSTANCE : ExpenseMasterDataSource? = null
        fun INSTANCE(context: Context) : ExpenseMasterDataSource? {
            if(INSTANCE == null)
                INSTANCE = ExpenseMasterLocalDataSource(context)
            return INSTANCE
        }
    }
    override fun expenseMaster(month: String, year: String): Single<List<ExpenseMaster>> {
        return DATABASE_INSTANCE.expenseMasterDao().expenseMaster(month, year)
    }
}