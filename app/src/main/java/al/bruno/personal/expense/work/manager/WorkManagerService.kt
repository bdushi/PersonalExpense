package al.bruno.personal.expense.work.manager

import al.bruno.personal.expense.data.source.ExpenseRepository
import al.bruno.personal.expense.model.Expense
import android.content.Context
import android.util.Log
import androidx.work.ListenableWorker
import androidx.work.Worker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import com.google.firebase.database.DatabaseReference
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.*
import javax.inject.Inject

class WorkManagerService @AssistedInject constructor(@Assisted private val context: Context, @Assisted private val workerParams: WorkerParameters) : Worker(context, workerParams) {
    //, private val expenseRepository: ExpenseRepository, private val databaseReference: DatabaseReference
    /*@Inject
    lateinit var databaseReference: DatabaseReference
    @Inject
    lateinit var expenseRepository: ExpenseRepository*/
    private val calendar = Calendar.getInstance()
    private val disposable = CompositeDisposable()

    override fun doWork(): Result {
        /*disposable
                .add(expenseRepository
                        .expenses()
                        .subscribeOn(Schedulers.io())
                        .subscribe ({
                            val expense = java.util.HashMap<kotlin.String, kotlin.Any>()
                            expense.put("expenses", it)
                            val key = databaseReference.child("expense").push().key
                            val childUpdates = java.util.HashMap<kotlin.String, kotlin.Any>()
                            childUpdates.put("/expense/" + key, expense)
                            databaseReference.updateChildren(childUpdates)
                        }, {
                            Log.e(WorkManagerService::class.java.name, it.message, it)
                        })
                )*/
        return Result.success()
        /*if((calendar[Calendar.MONTH] == 1 ||
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
                        calendar[Calendar.MONTH] == 12) &&
                calendar[Calendar.DAY_OF_MONTH] == 1) {
            disposable
                    .addAll(settingsDao!!
                    .settings(1)
                    .subscribeOn(Schedulers.io())
                    .subscribe({ b ->
                val budget = Incomes()
                budget.incomes = b.incomes
                budget.date = DateTime(calendar.timeInMillis).withTimeAtStartOfDay()
                disposable.add(provideBudgetInjection(context)!!.insert(budget).subscribeOn(Schedulers.io()).subscribe({
                }, {

                }))
            }, {

            }))
            return Result.success()
        } else {
            return Result.failure()
        }*/
    }
    @AssistedInject.Factory
    interface Factory : ChildWorkerFactory

    override fun onStopped() {
        disposable.clear()
    }
}