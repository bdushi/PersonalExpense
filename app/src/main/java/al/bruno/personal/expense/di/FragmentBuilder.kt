package al.bruno.personal.expense.di

import dagger.android.ContributesAndroidInjector

abstract class FragmentBuilder {
    @ActivityScoped
    @ContributesAndroidInjector(modules = [ExpenseModule::class])
    abstract fun expenseModule(): ExpenseModule
}
