package al.bruno.personal.expense.work.manager

import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
internal interface WorkerBindingModule {
    @Binds
    @IntoMap
    @WorkerKey(PushExpenseWorkManager::class)
    fun bindWorkManagerService(factory: PushExpenseWorkManager.Factory): ChildWorkerFactory
    @Binds
    @IntoMap
    @WorkerKey(PullExpenseWorkManager::class)
    fun bindPullExpenseWorkManager(factory: PullExpenseWorkManager.Factory): ChildWorkerFactory
}
