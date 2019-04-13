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

class PushExpenseWorkManager @AssistedInject constructor(
        @Assisted private val context: Context,
        @Assisted  private val workerParams: WorkerParameters,
        private val syncRepository: SyncRepository) : Worker(context, workerParams) {
    private val disposable = CompositeDisposable()
    private val databaseReference = FirebaseDatabase.getInstance().reference
    private val uid = FirebaseAuth.getInstance().currentUser!!.uid

    override fun doWork(): Result {
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
