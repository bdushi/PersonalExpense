package al.bruno.financaime.dependency.injection

import al.bruno.financaime.data.source.ExpenseDataSource
import al.bruno.financaime.data.source.ExpenseRepository
import al.bruno.financaime.data.source.local.ExpenseLocalDataSource
import android.content.Context
import androidx.annotation.NonNull

object ExpenseInjection {
    fun providerExpenseInjection(@NonNull context: Context) : ExpenseDataSource? {
        return ExpenseRepository.newInstance(ExpenseLocalDataSource.INSTANCE(context)!!)
    }
}