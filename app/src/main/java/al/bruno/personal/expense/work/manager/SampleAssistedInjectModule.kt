package al.bruno.personal.expense.work.manager

import com.squareup.inject.assisted.dagger2.AssistedModule
import dagger.Module

@Module(includes = [AssistedInject_SampleAssistedInjectModule::class])
@AssistedModule
interface SampleAssistedInjectModule