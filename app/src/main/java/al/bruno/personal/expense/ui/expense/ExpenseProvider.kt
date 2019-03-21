package al.bruno.personal.expense.ui.expense

import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ExpenseProvider {
    @ContributesAndroidInjector(modules = [ExpenseModule::class])
    internal abstract fun providePersonalExpensesFragment(): PersonalExpensesFragment
}