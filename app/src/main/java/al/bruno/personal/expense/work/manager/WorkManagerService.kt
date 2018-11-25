package al.bruno.personal.expense.work.manager

import al.bruno.personal.expense.dependency.injection.InjectionProvider.provideBudgetInjection
import al.bruno.personal.expense.dependency.injection.InjectionProvider.providerSettingsInjection
import al.bruno.personal.expense.model.Budget
import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.*

class WorkManagerService(val context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {
    private val calendar = Calendar.getInstance()
    private val disposable = CompositeDisposable()
    override fun doWork(): Result {
        if((
                        calendar[Calendar.MONTH] == 1 ||
                        calendar[Calendar.MONTH] == 2 ||
                        calendar[Calendar.MONTH] == 3 ||
                        calendar[Calendar.MONTH] == 4 ||
                        calendar[Calendar.MONTH] == 5 ||
                        calendar[Calendar.MONTH] == 6 ||
                        calendar[Calendar.MONTH] == 7 ||
                        calendar[Calendar.MONTH] == 8 ||
                        calendar[Calendar.MONTH] == 9 ||
                        calendar[Calendar.MONTH] == 10 ||
                        calendar[Calendar.MONTH] == 11 ||
                        calendar[Calendar.MONTH] == 12) && calendar[Calendar.DAY_OF_MONTH] == 1) {
            disposable.addAll(providerSettingsInjection(context)!!.settings(1).subscribeOn(Schedulers.io()).subscribe({ b ->
                val budget = Budget()
                budget.budget = b.budget
                budget.incomes = b.incomes
                budget.date = calendar.time
                disposable.add(provideBudgetInjection(context)!!.insert(budget).subscribeOn(Schedulers.io()).subscribe({
                }, {

                }))
            }, {

            }))
            return Result.SUCCESS
        } else {
            return Result.FAILURE
        }
    }

    override fun onStopped() {
        disposable.clear()
    }
}