package ru.geekbrains.android.level3.valeryvpetrov.di.component

import android.content.Context
import dagger.Component
import ru.geekbrains.android.level3.valeryvpetrov.di.UseCaseScope
import ru.geekbrains.android.level3.valeryvpetrov.di.module.UserUseCaseModule
import ru.geekbrains.android.level3.valeryvpetrov.domain.usecase.DeleteUserUseCase
import ru.geekbrains.android.level3.valeryvpetrov.domain.usecase.GetUserLocalUseCase
import ru.geekbrains.android.level3.valeryvpetrov.domain.usecase.GetUserRemoteUseCase
import ru.geekbrains.android.level3.valeryvpetrov.domain.usecase.SaveUserUseCase

@Component(
    dependencies = [
        ApplicationComponent::class
    ],
    modules = [
        UserUseCaseModule::class
    ]
)
@UseCaseScope
interface UserComponent {

    fun context(): Context

    fun getUserRemoteUseCase(): GetUserRemoteUseCase

    fun getUserLocalUseCase(): GetUserLocalUseCase

    fun saveUserUseCase(): SaveUserUseCase

    fun deleteUserUseCase(): DeleteUserUseCase
}