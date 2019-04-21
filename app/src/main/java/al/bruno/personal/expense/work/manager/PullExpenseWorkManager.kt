package al.bruno.personal.expense.work.manager

import al.bruno.personal.expense.data.source.CategoriesRepository
import al.bruno.personal.expense.data.source.ExpenseRepository
import al.bruno.personal.expense.sync.SyncService
import al.bruno.personal.expense.ui.HostActivity
import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class PullExpenseWorkManager @AssistedInject constructor(
        @Assisted private val context: Context,
        @Assisted private val workerParams: WorkerParameters,
        private val categoriesRepository: CategoriesRepository,
        private val expenseRepository: ExpenseRepository) : Worker(context, workerParams) {
    private val disposable = CompositeDisposable()
    override fun doWork(): Result {
        //userInfo!!.uid
        FirebaseDatabase.getInstance().reference.child(inputData.getString("UID")!!).addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                Log.d(HostActivity::class.java.name, "onCancelled", p0.toException())
            }

            override fun onDataChange(p0: DataSnapshot) {
                val syncService = p0.getValue(SyncService::class.java)
                if (syncService != null) {
                    disposable
                            .addAll(
                                    expenseRepository.insert(syncService.expenseConvert())
                                            .subscribeOn(Schedulers.io())
                                            .subscribe {
                                                Log.d(HostActivity::class.java.name, "Expense synced")
                                            },
                                    categoriesRepository
                                            .insert(syncService.categoriesConvert())
                                            .subscribeOn(Schedulers.io())
                                            .subscribe {
                                                Log.d(HostActivity::class.java.name, "Categories synced")
                                            }
                            )
                }
                Log.d(HostActivity::class.java.name, "onDataChange$syncService")
            }
        })
        return Result.success()
    }

    override fun onStopped() {
        disposable.clear()
    }
    @AssistedInject.Factory
    interface Factory : ChildWorkerFactory
}