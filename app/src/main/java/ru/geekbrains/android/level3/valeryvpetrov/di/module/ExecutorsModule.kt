package ru.geekbrains.android.level3.valeryvpetrov.di.module

import dagger.Module
import dagger.Provides
import ru.geekbrains.android.level3.valeryvpetrov.data.executor.DiskExecutionScheduler
import ru.geekbrains.android.level3.valeryvpetrov.data.executor.NetworkExecutionScheduler
import ru.geekbrains.android.level3.valeryvpetrov.di.QualifierDiskExecutionScheduler
import ru.geekbrains.android.level3.valeryvpetrov.di.QualifierMainThreadExecutionScheduler
import ru.geekbrains.android.level3.valeryvpetrov.di.QualifierNetworkExecutionScheduler
import ru.geekbrains.android.level3.valeryvpetrov.domain.executor.IExecutionScheduler
import ru.geekbrains.android.level3.valeryvpetrov.domain.executor.IPostExecutionScheduler
import ru.geekbrains.android.level3.valeryvpetrov.presentation.executor.MainThreadExecutionScheduler
import javax.inject.Singleton

@Module
class ExecutorsModule {

    @Provides
    @QualifierMainThreadExecutionScheduler
    @Singleton
    fun provideMainThreadExecutionScheduler(): IPostExecutionScheduler =
        MainThreadExecutionScheduler()

    @Provides
    @QualifierNetworkExecutionScheduler
    @Singleton
    fun provideNetworkExecutionScheduler(): IExecutionScheduler =
        NetworkExecutionScheduler()

    @Provides
    @QualifierDiskExecutionScheduler
    @Singleton
    fun provideDiskExecutionScheduler(): IExecutionScheduler =
        DiskExecutionScheduler()
}