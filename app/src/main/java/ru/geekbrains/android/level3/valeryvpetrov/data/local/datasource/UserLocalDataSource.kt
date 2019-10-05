package ru.geekbrains.android.level3.valeryvpetrov.data.local.datasource

import io.reactivex.Completable
import io.reactivex.Single
import ru.geekbrains.android.level3.valeryvpetrov.data.local.realm.datasource.UserRealmDataSource
import ru.geekbrains.android.level3.valeryvpetrov.data.local.room.datasource.UserRoomDataSource
import ru.geekbrains.android.level3.valeryvpetrov.data.repository.datasource.IUserDataSource
import ru.geekbrains.android.level3.valeryvpetrov.domain.entity.RepoItem
import ru.geekbrains.android.level3.valeryvpetrov.domain.entity.User

class UserLocalDataSource(
    private val userRealmDataSource: UserRealmDataSource,
    private val userRoomDataSource: UserRoomDataSource
) : IUserDataSource {

    override fun getUser(username: String): Single<User> {
        return userRoomDataSource.getUser(username)
//        return Single.ambArray(
//            userRealmDataSource.getUser(username),
//            userRoomDataSource.getUser(username)
//        )
    }

    override fun getUserRepos(user: User): Single<List<RepoItem>?> {
        return userRoomDataSource.getUserRepos(user)
//        return Single.ambArray(
//            userRealmDataSource.getUserRepos(user),
//            userRoomDataSource.getUserRepos(user)
//        )
    }

    override fun saveUser(user: User): Completable {
        return userRoomDataSource.saveUser(user)
//        return Completable.concatArray(
//            userRealmDataSource.saveUser(user),
//            userRoomDataSource.saveUser(user)
//        )
    }

    override fun deleteUser(user: User): Completable {
        return userRoomDataSource.deleteUser(user)
//        return Completable.concatArray(
//            userRealmDataSource.deleteUser(user),
//            userRoomDataSource.deleteUser(user)
//        )
    }
}
