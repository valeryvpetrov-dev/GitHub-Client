package ru.geekbrains.android.level3.valeryvpetrov.data.local.datasource

import io.reactivex.Completable
import io.reactivex.Single
import ru.geekbrains.android.level3.valeryvpetrov.data.local.realm.datasource.UserRealmDataSource
import ru.geekbrains.android.level3.valeryvpetrov.data.local.room.datasource.UserRoomDataSource
import ru.geekbrains.android.level3.valeryvpetrov.data.repository.datasource.IUserDataSource
import ru.geekbrains.android.level3.valeryvpetrov.domain.entity.RepoItem
import ru.geekbrains.android.level3.valeryvpetrov.domain.entity.User
import javax.inject.Inject

class UserLocalDataSource
@Inject constructor(
    private val userRealmDataSource: UserRealmDataSource,
    private val userRoomDataSource: UserRoomDataSource
) : IUserDataSource {

    override fun getUser(username: String): Single<User> {
        return Single.ambArray(
            userRealmDataSource.getUser(username),
            userRoomDataSource.getUser(username)
        ).onErrorResumeNext {
            Single.error(Throwable("There is no user with login $username in database"))
        }
    }

    override fun getUserRepos(user: User): Single<List<RepoItem>?> {
        return Single.ambArray(
            userRealmDataSource.getUserRepos(user),
            userRoomDataSource.getUserRepos(user)
        ).onErrorResumeNext {
            Single.error(Throwable("There is no repos ${user.login} owns in database"))
        }
    }

    override fun saveUser(user: User): Completable {
        return Completable.concatArray(
            userRealmDataSource.saveUser(user),
            userRoomDataSource.saveUser(user)
        ).onErrorResumeNext {
            Completable.error(Throwable("There is already exists user with login ${user.login} in database"))
        }
    }

    override fun deleteUser(user: User): Completable {
        return Completable.concatArray(
            userRealmDataSource.deleteUser(user),
            userRoomDataSource.deleteUser(user)
        ).onErrorResumeNext {
            Completable.error(Throwable("There is no user with login ${user.login} to delete"))
        }
    }
}
