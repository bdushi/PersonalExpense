package al.bruno.personal.expense.work.manager

import al.bruno.personal.expense.data.source.SyncRepository
import al.bruno.personal.expense.sync.Categories
import al.bruno.personal.expense.sync.Expense
import al.bruno.personal.expense.sync.SyncService
import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import java.util.*

class PushExpenseWorkManager @AssistedInject constructor(@Assisted private val context: Context, @Assisted  private val workerParams: WorkerParameters, private val syncRepository: SyncRepository) : Worker(context, workerParams) {
    //, private val expenseRepository: ExpenseRepository, private val databaseReference: DatabaseReference
    /*@Inject
    lateinit var databaseReference: DatabaseReference
    @Inject
    lateinit var expenseRepository: ExpenseRepository*/
    private val calendar = Calendar.getInstance()
    private val disposable = CompositeDisposable()
    private val databaseReference = FirebaseDatabase.getInstance().reference
    private val uid = FirebaseAuth.getInstance().currentUser!!.uid

    override fun doWork(): Result {
        /*expenseRepository
                .expenses()
                .subscribeOn(Schedulers.io())
                .subscribe ({
                    expense["expenses"] = it
                    val key = databaseReference.child("expense").push().key
                    val childUpdates = HashMap<kotlin.String, kotlin.Any>()
                    childUpdates["/expense/$key"] = expense
                    databaseReference.updateChildren(expense)
                }, {
                    Log.d(PushExpenseWorkManager::class.java.name, it.message, it)
                })*/
        val expense = HashMap<String, Any>()
        val receiveLockObservable = Single.zip(
                syncRepository.expenses().subscribeOn(Schedulers.io()),
                syncRepository.categories().subscribeOn(Schedulers.io()),
                BiFunction<List<Expense>, List<Categories>, SyncService>{ list: List<Expense>, list1: List<Categories> -> SyncService(list, list1) })
        disposable.add(receiveLockObservable
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe({
                    if(it.expenses.isNotEmpty() || it.categories.isNotEmpty()) {
                        expense["expenses"] = it.expenses
                        expense["categories"] = it.categories
                        val user = HashMap<kotlin.String, Any>()
                        user[uid] = expense
                        databaseReference.updateChildren(user)
                    } else {
                        Log.d(PushExpenseWorkManager::class.java.name, "No-data")
                    }
                },{
                    Log.d(PushExpenseWorkManager::class.java.name, it.message, it)
                }))
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

    /*class Factory @Inject constructor(private val syncRepository: Provider<SyncRepository>) : SimpleWorkerFactory {
        override fun create(context: Context, workerParams: WorkerParameters): ListenableWorker {
            return PushExpenseWorkManager(context, workerParams, syncRepository.get())
        }
    }*/

    override fun onStopped() {
        disposable.clear()
    }
}
