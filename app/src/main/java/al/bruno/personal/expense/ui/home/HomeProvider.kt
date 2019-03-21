package al.bruno.personal.expense.ui.home

import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class HomeProvider {
    @ContributesAndroidInjector(modules = [HomeModule::class])
    internal abstract fun provideHomeFragment(): HomeFragment
}