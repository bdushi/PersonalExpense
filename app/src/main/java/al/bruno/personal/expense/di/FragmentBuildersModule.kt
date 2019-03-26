package al.bruno.personal.expense.di

import al.bruno.personal.expense.dialog.edit.expense.EditExpenseBottomSheet
import al.bruno.personal.expense.ui.details.DetailsFragment
import al.bruno.personal.expense.ui.expense.ExpenseFragment
import al.bruno.personal.expense.ui.home.HomeFragment
import al.bruno.personal.expense.ui.statistic.StatisticsFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBuildersModule {
    @ContributesAndroidInjector
    internal abstract fun contributeHomeFragment(): HomeFragment
    @ContributesAndroidInjector
    internal abstract fun contributePersonalExpensesFragment(): ExpenseFragment
    @ContributesAndroidInjector
    internal abstract fun contributeStatisticsFragment(): StatisticsFragment
    @ContributesAndroidInjector
    internal abstract fun contributeDetailsFragment(): DetailsFragment
    @ContributesAndroidInjector
    internal abstract fun contributeEditExpenseBottomSheet(): EditExpenseBottomSheet

}