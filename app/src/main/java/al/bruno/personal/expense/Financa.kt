package al.bruno.personal.expense

import al.bruno.personal.expense.work.manager.WorkManagerService
import android.app.Application
import androidx.work.WorkManager
import androidx.work.PeriodicWorkRequest
import java.util.concurrent.TimeUnit
import androidx.work.ExistingPeriodicWorkPolicy

class Financa : Application() {
    val ACTION_PROCESS_UPDATES = " al.bruno.financaime.PROCESS_UPDATES"
    override fun onCreate() {
        super.onCreate()
        //google analitics
        //FirebaseAnalytics.getInstance(this)
        /*WorkManager
                .getInstance()
                .beginUniqueWork(ACTION_PROCESS_UPDATES, ExistingWorkPolicy.REPLACE,
                        OneTimeWorkRequest.Builder(WorkManagerService::class.java).build())
                .enqueue()*/
        WorkManager
                .getInstance()
                .enqueueUniquePeriodicWork(ACTION_PROCESS_UPDATES, ExistingPeriodicWorkPolicy.KEEP,
                        PeriodicWorkRequest
                                .Builder(WorkManagerService::class.java, 24, TimeUnit.HOURS, 3, TimeUnit.HOURS)
                                .addTag(ACTION_PROCESS_UPDATES)
                                .build())
    }
}