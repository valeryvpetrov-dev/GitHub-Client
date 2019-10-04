package ru.geekbrains.android.level3.valeryvpetrov.data.repository

import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.functions.Function
import ru.geekbrains.android.level3.valeryvpetrov.data.repository.datasource.IUserDataSource
import ru.geekbrains.android.level3.valeryvpetrov.domain.entity.RepoItem
import ru.geekbrains.android.level3.valeryvpetrov.domain.entity.User
import ru.geekbrains.android.level3.valeryvpetrov.domain.repository.IUserRepository as DomainUserRepository

class UserRepository(
    private val userRemoteDataSource: IUserDataSource,
    private val userLocalDataSource: IUserDataSource
) : DomainUserRepository {

    companion object {

        private var instance: UserRepository? = null

        fun getInstance(
            userRemoteRepository: IUserDataSource,
            userLocalRepository: IUserDataSource
        ) =
            instance
                ?: UserRepository(
                    userRemoteRepository,
                    userLocalRepository
                ).apply {
                    instance = this
                }
    }

    override fun getUser(username: String): Single<User> {
        // first try to get from local repository
        return userLocalDataSource.getUser(username)
            .onErrorResumeNext {
                // if there is no required data redirect call to remote repository
                userRemoteDataSource.getUser(username)
                    .doOnError {
                        Throwable("Problem with getting user")
                    }
                    .flatMap(Function<User, Single<User>> { user ->
                        return@Function getUserRepos(user)
                            .map {
                                user.repoItems = it
                                return@map user
                            }
                    })
            }
            .flatMap(Function<User, Single<User>> { user ->
                return@Function getUserRepos(user)
                    .map {
                        user.repoItems = it
                        return@map user
                    }
            })
    }

    override fun getUserRepos(user: User): Single<List<RepoItem>?> {
        // first try to get from local repository
        return userLocalDataSource.getUserRepos(user)
            .onErrorResumeNext {
                // if there is no required data redirect call to remote repository
                userRemoteDataSource.getUserRepos(user)
                    .doOnError {
                        Throwable("Problem with getting user repos")
                    }
            }
    }

    override fun saveUser(user: User): Completable {
        return userLocalDataSource.saveUser(user)
    }

    override fun deleteUser(user: User): Completable {
        return userLocalDataSource.deleteUser(user)
    }
}