package ru.geekbrains.android.level3.valeryvpetrov.domain.repository

import androidx.annotation.WorkerThread
import io.reactivex.Single
import ru.geekbrains.android.level3.valeryvpetrov.domain.entity.RepoItem
import ru.geekbrains.android.level3.valeryvpetrov.domain.entity.User
import ru.geekbrains.android.level3.valeryvpetrov.domain.entity.UserItem

interface UserRepository {

    @WorkerThread
    fun getUsers(): Single<List<UserItem>>

    @WorkerThread
    fun getUser(username: String): Single<User>

    @WorkerThread
    fun getUserRepos(username: String): Single<List<RepoItem>>
}