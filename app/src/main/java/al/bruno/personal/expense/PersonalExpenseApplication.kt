package al.bruno.personal.expense

import al.bruno.personal.expense.di.DaggerAppComponent
import android.app.Activity
import android.app.Application
import com.crashlytics.android.Crashlytics
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import io.fabric.sdk.android.Fabric
import javax.inject.Inject

class PersonalExpenseApplication : Application(), HasActivityInjector {
    @Inject
    lateinit var activityInjector: DispatchingAndroidInjector<Activity>
    override fun onCreate() {
        super.onCreate()
        DaggerAppComponent.builder().application(this).build().inject(this)
        Fabric.with(this, Crashlytics())
    }

    override fun activityInjector(): AndroidInjector<Activity>? {
        return activityInjector
    }
}