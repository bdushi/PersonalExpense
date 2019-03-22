package al.bruno.personal.expense.di

import al.bruno.personal.expense.PersonalExpenseApplication
import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AndroidSupportInjectionModule::class,
    AppModule::class,
    ActivityBindingModule::class])
interface AppComponent : AndroidInjector<PersonalExpenseApplication> {
    fun inject(mainApplication: Application)
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder
        fun build(): AppComponent
    }
    //override fun inject(app: PersonalExpenseApplication)
}