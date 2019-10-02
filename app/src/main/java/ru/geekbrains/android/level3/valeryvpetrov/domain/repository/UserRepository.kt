package ru.geekbrains.android.level3.valeryvpetrov.domain.repository

import androidx.annotation.WorkerThread
import io.reactivex.Single
import ru.geekbrains.android.level3.valeryvpetrov.domain.entity.RepoItem
import ru.geekbrains.android.level3.valeryvpetrov.domain.entity.User
import ru.geekbrains.android.level3.valeryvpetrov.domain.entity.UserItem

interface UserRepository {

    @WorkerThread
    fun getUser(username: String): Single<User>

    @WorkerThread
    fun getUserRepos(user: User): Single<List<RepoItem>>
}