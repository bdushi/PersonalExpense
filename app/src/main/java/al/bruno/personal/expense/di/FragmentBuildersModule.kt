package al.bruno.personal.expense.di

import al.bruno.personal.expense.ui.expense.PersonalExpensesFragment
import al.bruno.personal.expense.ui.home.HomeFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBuildersModule {
    @ContributesAndroidInjector
    abstract fun contributeHomeFragment(): HomeFragment
    @ContributesAndroidInjector
    abstract fun contributePersonalExpensesFragment(): PersonalExpensesFragment
}