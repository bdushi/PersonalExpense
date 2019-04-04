package al.bruno.personal.expense.work.manager

import al.bruno.personal.expense.data.source.CategoriesRepository
import al.bruno.personal.expense.data.source.ExpenseRepository
import al.bruno.personal.expense.model.Categories
import al.bruno.personal.expense.model.Expense
import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.work.ListenableWorker
import androidx.work.Worker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import com.crashlytics.android.answers.PurchaseEvent
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.BiFunction
import io.reactivex.functions.Function3
import io.reactivex.schedulers.Schedulers
import java.util.*
import javax.inject.Inject
import javax.inject.Provider

class WorkManagerService constructor(context: Context, workerParams: WorkerParameters, private val expenseRepository: ExpenseRepository, private val categoriesRepository: CategoriesRepository) : Worker(context, workerParams) {
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
                    Log.d(WorkManagerService::class.java.name, it.message, it)
                })*/
        val expense = HashMap<String, Any>()
        val receiveLockObservable = Single.zip(
                expenseRepository.expenses().subscribeOn(Schedulers.io()),
                categoriesRepository.categories().subscribeOn(Schedulers.io()),
                BiFunction<List<Expense>, List<Categories>, PushExpense>{ list: List<Expense>, list1: List<Categories> -> PushExpense(list, list1) })
        disposable.add(receiveLockObservable
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe({
                    expense["expenses"] = it.expenses
                    expense["categories"] = it.categories
                    val user = HashMap<kotlin.String, Any>()
                    user[uid] = expense
                    databaseReference.updateChildren(user)
                },{
                    Log.d(WorkManagerService::class.java.name, it.message, it)
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

    /*@AssistedInject.Factory
    interface Factory : ChildWorkerFactory*/

    class Factory @Inject constructor(private val expenseRepository: Provider<ExpenseRepository>, private val categoriesRepository: Provider<CategoriesRepository>) : ChildWorkerFactory {
        override fun create(context: Context, workerParams: WorkerParameters): ListenableWorker {
            return WorkManagerService(context, workerParams, expenseRepository.get(), categoriesRepository.get())
        }
    }

    override fun onStopped() {
        disposable.clear()
    }
}
