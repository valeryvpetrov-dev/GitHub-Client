package ru.geekbrains.android.level3.valeryvpetrov.data.local.room.datasource

import android.util.Log
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.functions.Function
import ru.geekbrains.android.level3.valeryvpetrov.data.local.room.dao.RepoDao
import ru.geekbrains.android.level3.valeryvpetrov.data.local.room.dao.UserDao
import ru.geekbrains.android.level3.valeryvpetrov.data.local.room.entity.mapper.mapToRoom
import ru.geekbrains.android.level3.valeryvpetrov.data.repository.datasource.IUserDataSource
import ru.geekbrains.android.level3.valeryvpetrov.domain.entity.RepoItem
import ru.geekbrains.android.level3.valeryvpetrov.domain.entity.User
import ru.geekbrains.android.level3.valeryvpetrov.domain.entity.mapper.mapToDomain
import ru.geekbrains.android.level3.valeryvpetrov.util.measureTimeMillis

class UserRoomDataSource(
    private val userDao: UserDao,
    private val repoDao: RepoDao
) : IUserDataSource {

    companion object {

        const val LOG_TAG_EXECUTION_TIME = "ExecutionTime"

        const val LOG_MESSAGE_TEMPLATE_EXECUTION_TIME = "%-10s :: %-20s :: %.5f secs"
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
        val single = measureTimeMillis(block, { time ->
            Log.i(
                LOG_TAG_EXECUTION_TIME,
                LOG_MESSAGE_TEMPLATE_EXECUTION_TIME.format("Room", "getUser", time)
            )
        })
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
        val single = measureTimeMillis(block, { time ->
            Log.i(
                LOG_TAG_EXECUTION_TIME,
                LOG_MESSAGE_TEMPLATE_EXECUTION_TIME.format("Room", "getUserRepos", time)
            )
        })
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
        val completable = measureTimeMillis(block, { time ->
            Log.i(
                LOG_TAG_EXECUTION_TIME,
                LOG_MESSAGE_TEMPLATE_EXECUTION_TIME.format("Room", "saveUser", time)
            )
        })
        return completable
    }

    override fun deleteUser(user: User): Completable {
        val block: () -> Completable = {
            val (userRoom, repoItemsRoom) = user.mapToRoom()
            userDao.delete(userRoom)
        }

        val completable = measureTimeMillis(block, { time ->
            Log.i(
                LOG_TAG_EXECUTION_TIME,
                LOG_MESSAGE_TEMPLATE_EXECUTION_TIME.format("Room", "deleteUser", time)
            )
        })
        return completable
    }
}
