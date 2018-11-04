package al.bruno.financaime.view.model

import al.bruno.financaime.data.source.ExpenseDataSource
import al.bruno.financaime.data.source.ExpenseRepository
import al.bruno.financaime.dependency.injection.ExpenseInjection
import al.bruno.financaime.model.Expense
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import io.reactivex.Single
import java.util.*
import kotlin.math.exp

class ExpenseViewModel(application: Application) : AndroidViewModel(application), ExpenseDataSource {
    private var expenseRepository: ExpenseRepository
    init {
        expenseRepository = ExpenseInjection.providerExpenseInjection(application)!!
    }
    override fun insert(expense: Expense): Single<Long> {
        return expenseRepository.insert(expense)
    }

    override fun expense(id: Long): LiveData<Expense> {
        return expenseRepository.expense(id)
    }

    override fun expenses(month: String): LiveData<List<Expense>> {
        return expenseRepository.expenses(month)
    }

    override fun expenses(month: String, year: String): LiveData<List<Expense>> {
        return expenseRepository.expenses(month, year)
    }

    override fun date(): Single<List<Date>> {
        return expenseRepository.date()
    }
}