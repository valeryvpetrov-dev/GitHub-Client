package ru.geekbrains.android.level3.valeryvpetrov.data.repository.datasource

import io.reactivex.Completable
import io.reactivex.Single
import ru.geekbrains.android.level3.valeryvpetrov.domain.entity.RepoItem
import ru.geekbrains.android.level3.valeryvpetrov.domain.entity.User

interface IUserDataSource {

    fun getUser(username: String): Single<User>

    fun getUserRepos(user: User): Single<List<RepoItem>?>

    fun saveUser(user: User): Completable

    fun deleteUser(user: User): Completable
}