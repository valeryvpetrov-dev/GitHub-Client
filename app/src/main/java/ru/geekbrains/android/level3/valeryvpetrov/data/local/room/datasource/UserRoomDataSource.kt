package ru.geekbrains.android.level3.valeryvpetrov.data.local.room.datasource

import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.functions.Function
import ru.geekbrains.android.level3.valeryvpetrov.data.local.room.dao.RepoDao
import ru.geekbrains.android.level3.valeryvpetrov.data.local.room.dao.UserDao
import ru.geekbrains.android.level3.valeryvpetrov.data.local.room.entity.mapper.mapToRoom
import ru.geekbrains.android.level3.valeryvpetrov.data.repository.datasource.IUserDataSource
import ru.geekbrains.android.level3.valeryvpetrov.data.util.logcatInfo
import ru.geekbrains.android.level3.valeryvpetrov.data.util.measureTimeInSeconds
import ru.geekbrains.android.level3.valeryvpetrov.domain.entity.RepoItem
import ru.geekbrains.android.level3.valeryvpetrov.domain.entity.User
import ru.geekbrains.android.level3.valeryvpetrov.domain.entity.mapper.mapToDomain

class UserRoomDataSource(
    private val userDao: UserDao,
    private val repoDao: RepoDao
) : IUserDataSource {

    companion object {

        const val LOG_MESSAGE_NAMESPACE = "Room"
    }

    override fun getUser(username: String): Single<User> {
        val block: () -> Single<User> = {
            userDao.selectByLogin(username)
                .map { it.mapToDomain() }
                .flatMap(Function<User, Single<User>> { user ->
                    return@Function repoDao.selectByUserId(user.id)
                        .map { repoItems ->
                            repoItems.map { it.mapToDomain() }
                        }
                        .map { repoItems ->
                            user.repoItems = repoItems
                            return@map user
                        }
                })
                .onErrorResumeNext {
                    Single.error(Throwable("There is no user with login $username in Room"))
                }
        }
        val single =
            measureTimeInSeconds(block, LOG_MESSAGE_NAMESPACE, "getUser", logcatInfo)
        return single
    }


    override fun getUserRepos(user: User): Single<List<RepoItem>?> {
        val block: () -> Single<List<RepoItem>?> = {
            repoDao.selectByUserId(user.id)
                .map { repoItems ->
                    repoItems.map { it.mapToDomain() }
                }
                .onErrorResumeNext {
                    Single.error(Throwable("There is no repos ${user.login} owns in Room"))
                }
        }
        val single =
            measureTimeInSeconds(block, LOG_MESSAGE_NAMESPACE, "getUserRepos", logcatInfo)
        return single
    }

    override fun saveUser(user: User): Completable {
        val block: () -> Completable = {
            val (userRoom, repoItemsRoom) = user.mapToRoom()
            userDao.insert(userRoom)
                .onErrorResumeNext {
                    Completable.error(Throwable("There is already exists user with login ${user.login} in Room"))
                }
                .andThen(repoItemsRoom?.let {
                    repoDao.insert(it)
                        .onErrorResumeNext {
                            Completable.error(Throwable("There is already exists repo in Room"))
                        }
                })
        }
        val completable =
            measureTimeInSeconds(block, LOG_MESSAGE_NAMESPACE, "saveUser", logcatInfo)
        return completable
    }

    override fun deleteUser(user: User): Completable {
        val block: () -> Completable = {
            val (userRoom, repoItemsRoom) = user.mapToRoom()
            userDao.delete(userRoom)
        }

        val completable =
            measureTimeInSeconds(block, LOG_MESSAGE_NAMESPACE, "deleteUser", logcatInfo)
        return completable
    }
}
