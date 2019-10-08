package ru.geekbrains.android.level3.valeryvpetrov.di.module

import dagger.Module
import dagger.Provides
import ru.geekbrains.android.level3.valeryvpetrov.di.QualifierMainThreadExecutionScheduler
import ru.geekbrains.android.level3.valeryvpetrov.di.QualifierNetworkExecutionScheduler
import ru.geekbrains.android.level3.valeryvpetrov.di.UseCaseScope
import ru.geekbrains.android.level3.valeryvpetrov.domain.executor.IExecutionScheduler
import ru.geekbrains.android.level3.valeryvpetrov.domain.executor.IPostExecutionScheduler
import ru.geekbrains.android.level3.valeryvpetrov.domain.repository.IUserRepository
import ru.geekbrains.android.level3.valeryvpetrov.domain.usecase.DeleteUserUseCase
import ru.geekbrains.android.level3.valeryvpetrov.domain.usecase.GetUserLocalUseCase
import ru.geekbrains.android.level3.valeryvpetrov.domain.usecase.GetUserRemoteUseCase
import ru.geekbrains.android.level3.valeryvpetrov.domain.usecase.SaveUserUseCase

@Module
class UserUseCaseModule {

    @Provides
    @UseCaseScope
    fun provideGetUserRemoteUseCase(
        @QualifierNetworkExecutionScheduler
        networkExecutionScheduler: IExecutionScheduler,
        @QualifierMainThreadExecutionScheduler
        mainThreadExecutionScheduler: IPostExecutionScheduler,
        userRepository: IUserRepository
    ): GetUserRemoteUseCase =
        GetUserRemoteUseCase(
            networkExecutionScheduler,
            mainThreadExecutionScheduler,
            userRepository
        )

    @Provides
    @UseCaseScope
    fun provideGetUserLocalUseCase(
        @QualifierNetworkExecutionScheduler
        diskExecutionScheduler: IExecutionScheduler,
        @QualifierMainThreadExecutionScheduler
        mainThreadExecutionScheduler: IPostExecutionScheduler,
        userRepository: IUserRepository
    ): GetUserLocalUseCase =
        GetUserLocalUseCase(
            diskExecutionScheduler,
            mainThreadExecutionScheduler,
            userRepository
        )

    @Provides
    @UseCaseScope
    fun provideSaveUserUseCase(
        @QualifierNetworkExecutionScheduler
        diskExecutionScheduler: IExecutionScheduler,
        @QualifierMainThreadExecutionScheduler
        mainThreadExecutionScheduler: IPostExecutionScheduler,
        userRepository: IUserRepository
    ): SaveUserUseCase =
        SaveUserUseCase(
            diskExecutionScheduler,
            mainThreadExecutionScheduler,
            userRepository
        )

    @Provides
    @UseCaseScope
    fun provideDeleteUserUseCase(
        @QualifierNetworkExecutionScheduler
        diskExecutionScheduler: IExecutionScheduler,
        @QualifierMainThreadExecutionScheduler
        mainThreadExecutionScheduler: IPostExecutionScheduler,
        userRepository: IUserRepository
    ): DeleteUserUseCase =
        DeleteUserUseCase(
            diskExecutionScheduler,
            mainThreadExecutionScheduler,
            userRepository
        )
}