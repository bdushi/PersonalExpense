package al.bruno.personal.expense.di

import al.bruno.personal.expense.data.source.local.AppDatabase
import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
abstract class DatabaseModule {
    @Provides
    @Singleton
    fun providesDatabaseHelper(context: Context): AppDatabase {
        return AppDatabase.getInstance(context)
    }
}