package al.bruno.personal.expense.dependency.injection

import al.bruno.personal.expense.data.source.ExpenseDataSource
import al.bruno.personal.expense.data.source.ExpenseRepository
import al.bruno.personal.expense.data.source.local.ExpenseLocalDataSource
import android.content.Context
import androidx.annotation.NonNull

object ExpenseInjection {
    fun providerExpenseInjection(@NonNull context: Context) : ExpenseDataSource? {
        return ExpenseRepository.newInstance(ExpenseLocalDataSource.INSTANCE(context)!!)
    }
}