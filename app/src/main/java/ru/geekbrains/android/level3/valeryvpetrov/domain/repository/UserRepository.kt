package ru.geekbrains.android.level3.valeryvpetrov.domain.repository

import androidx.annotation.MainThread
import androidx.annotation.WorkerThread
import ru.geekbrains.android.level3.valeryvpetrov.domain.entity.RepoItem
import ru.geekbrains.android.level3.valeryvpetrov.domain.entity.User
import ru.geekbrains.android.level3.valeryvpetrov.domain.entity.UserItem
import ru.geekbrains.android.level3.valeryvpetrov.domain.usecase.GetUserReposUseCase
import ru.geekbrains.android.level3.valeryvpetrov.domain.usecase.GetUserUseCase

interface UserRepository {

    @MainThread
    interface GetUsersCallback {

        fun onSuccess(userItems: List<UserItem>)

        fun onError(throwable: Throwable)
    }

    @MainThread
    interface GetUserCallback {

        fun onSuccess(user: User)

        fun onError(throwable: Throwable)
    }

    @MainThread
    interface GetUserReposCallback {

        fun onSuccess(repoItems: List<RepoItem>)

        fun onError(throwable: Throwable)
    }

    @WorkerThread
    fun getUsers(callback: GetUsersCallback)

    @WorkerThread
    fun getUser(requestValue: GetUserUseCase.RequestValue, callback: GetUserCallback)

    @WorkerThread
    fun getUserRepos(requestValue: GetUserReposUseCase.RequestValue, callback: GetUserReposCallback)
}