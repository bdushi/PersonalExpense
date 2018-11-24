package al.bruno.personal.expense.view.model

import al.bruno.financaime.dependency.injection.InjectionProvider.providerExpenseInjection
import al.bruno.personal.expense.data.source.ExpenseDataSource
import al.bruno.personal.expense.model.Expense
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import io.reactivex.Single
import java.util.*

class ExpenseViewModel(application: Application) : AndroidViewModel(application), ExpenseDataSource {
    private var expenseRepository: ExpenseDataSource = providerExpenseInjection(application)!!
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