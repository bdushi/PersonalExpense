package al.bruno.personal.expense.di

import al.bruno.personal.expense.ui.HostActivity
import al.bruno.personal.expense.ui.profile.ProfileActivity
import al.bruno.personal.expense.ui.sign.`in`.SignInActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBindingModule {
    //, ExpenseProvider::class, HomeProvider::class
    // https://github.com/googlesamples/android-architecture-components/blob/master/GithubBrowserSample/app/src/main/java/com/android/example/github/di/MainActivityModule.kt
    // https://stackoverflow.com/questions/48534084/dagger-2-viewmodelprovider-factory-bound-multiple-times

    //@ContributesAndroidInjector(modules = [HostModule::class])
    @ActivityScoped
    @ContributesAndroidInjector(modules = [FragmentBuildersModule::class, ViewModelModule::class])
    abstract fun hostActivity(): HostActivity

    @ActivityScoped
    @ContributesAndroidInjector
    abstract fun signInActivity(): SignInActivity
    @ActivityScoped
    @ContributesAndroidInjector
    abstract fun profileActivity(): ProfileActivity
}
