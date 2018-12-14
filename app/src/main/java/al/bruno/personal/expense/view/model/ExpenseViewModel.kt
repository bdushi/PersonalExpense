package al.bruno.personal.expense.view.model

import al.bruno.personal.expense.dependency.injection.InjectionProvider.providerExpenseInjection
import al.bruno.personal.expense.data.source.ExpenseDataSource
import al.bruno.personal.expense.model.Expense
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import io.reactivex.Single
import org.joda.time.DateTime
class ExpenseViewModel(application: Application) : AndroidViewModel(application), ExpenseDataSource {
    private var expenseDataSource: ExpenseDataSource = providerExpenseInjection(application)!!
    override fun insert(expense: Expense): Single<Long> {
        return expenseDataSource.insert(expense)
    }

    override fun expense(id: Long): LiveData<Expense> {
        return expenseDataSource.expense(id)
    }

    override fun expenses(month: String): LiveData<List<Expense>> {
        return expenseDataSource.expenses(month)
    }

    override fun expenses(month: String, year: String): LiveData<List<Expense>> {
        return expenseDataSource.expenses(month, year)
    }

    override fun date(): Single<Array<DateTime>> {
        return expenseDataSource.date()
    }
    override fun expenses(date: DateTime): Single<List<Expense>> {
        return expenseDataSource.expenses(date)
    }

    override fun total(date: DateTime): Single<String> {
        return expenseDataSource.total(date)
    }
}