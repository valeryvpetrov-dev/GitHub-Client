package ru.geekbrains.android.level3.valeryvpetrov.domain.repository

import androidx.annotation.WorkerThread
import ru.geekbrains.android.level3.valeryvpetrov.domain.entity.User

interface UserRepository {

    interface GetUsersCallback {

        @WorkerThread
        fun onSuccess(users: List<User>)

        @WorkerThread
        fun onError(throwable: Throwable)
    }

    @WorkerThread
    fun getUsers(callback: GetUsersCallback)
}