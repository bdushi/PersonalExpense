package al.bruno.personal.expense.work.manager

import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
internal interface WorkerBindingModule {
    @Binds
    @IntoMap
    @WorkerKey(WorkManagerService::class)
    fun bindWorkManagerService(factory: WorkManagerService.Factory): ChildWorkerFactory
}
