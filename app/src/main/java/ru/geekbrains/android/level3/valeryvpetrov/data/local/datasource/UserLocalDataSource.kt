package ru.geekbrains.android.level3.valeryvpetrov.data.local.datasource

import io.reactivex.Completable
import io.reactivex.Single
import ru.geekbrains.android.level3.valeryvpetrov.data.local.realm.datasource.UserRealmDataSource
import ru.geekbrains.android.level3.valeryvpetrov.data.repository.datasource.IUserDataSource
import ru.geekbrains.android.level3.valeryvpetrov.domain.entity.RepoItem
import ru.geekbrains.android.level3.valeryvpetrov.domain.entity.User

class UserLocalDataSource(
    private val userRealmDataSource: UserRealmDataSource
) : IUserDataSource {

    override fun getUser(username: String): Single<User> {
        return Single.ambArray(userRealmDataSource.getUser(username))
    }

    override fun getUserRepos(user: User): Single<List<RepoItem>?> {
        return Single.ambArray(userRealmDataSource.getUserRepos(user))
    }

    override fun saveUser(user: User): Completable {
        return Completable.concatArray(userRealmDataSource.saveUser(user))
    }

    override fun deleteUser(user: User): Completable {
        return Completable.concatArray(userRealmDataSource.deleteUser(user))
    }
}
