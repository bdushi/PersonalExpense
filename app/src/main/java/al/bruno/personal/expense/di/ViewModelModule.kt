package al.bruno.personal.expense.di

import al.bruno.personal.expense.ui.home.HomeViewModel
import al.bruno.personal.expense.ui.HostViewModel
import al.bruno.personal.expense.ui.expense.ExpenseViewModel
import al.bruno.personal.expense.util.ViewModelProviderFactory
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(ExpenseViewModel::class)
    abstract fun bindExpenseViewModel(hostViewModel: ExpenseViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(HostViewModel::class)
    abstract fun bindHostViewModel(hostViewModel: HostViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    abstract fun bindHomeViewModel(homeViewModel: HomeViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelProviderFactory): ViewModelProvider.Factory
}