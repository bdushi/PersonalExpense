package al.bruno.personal.expense.di

import al.bruno.personal.expense.data.source.ExpenseRepository
import al.bruno.personal.expense.util.ViewModelProviderFactory
import al.bruno.personal.expense.view.model.ExpenseViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Module
import dagger.Provides

@Module
class ExpenseModule {
    @Provides
    internal fun provideMainViewModel(userRepository: ExpenseRepository): ExpenseViewModel {
        return ExpenseViewModel(userRepository)
    }

    @Provides
    fun mainViewModelProvider(expenseViewModel: ExpenseViewModel): ViewModelProvider.Factory {
        return ViewModelProviderFactory(expenseViewModel)
    }
}
