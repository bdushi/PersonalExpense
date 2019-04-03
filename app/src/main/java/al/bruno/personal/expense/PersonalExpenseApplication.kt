package al.bruno.personal.expense

import al.bruno.personal.expense.di.DaggerAppComponent
import al.bruno.personal.expense.factory.SampleWorkerFactory
import android.app.Activity
import android.app.Application
import androidx.work.Configuration
import androidx.work.WorkManager
import androidx.work.WorkerFactory
import com.crashlytics.android.Crashlytics
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import io.fabric.sdk.android.Fabric
import javax.inject.Inject

class PersonalExpenseApplication : Application(), HasActivityInjector {
    @Inject
    lateinit var activityInjector: DispatchingAndroidInjector<Activity>
    @Inject
    lateinit var workerFactory: SampleWorkerFactory
    override fun onCreate() {
        super.onCreate()
        DaggerAppComponent
                .builder()
                .application(this)
                .build()
                .inject(this)
        Fabric
                .with(this, Crashlytics())
        WorkManager
                .initialize(this, Configuration.Builder()
                .setWorkerFactory(workerFactory).build())
    }

    override fun activityInjector(): AndroidInjector<Activity>? {
        return activityInjector
    }
}