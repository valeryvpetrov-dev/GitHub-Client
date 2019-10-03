package ru.geekbrains.android.level3.valeryvpetrov.data.repository

import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.functions.Function
import ru.geekbrains.android.level3.valeryvpetrov.domain.entity.RepoItem
import ru.geekbrains.android.level3.valeryvpetrov.domain.entity.User
import ru.geekbrains.android.level3.valeryvpetrov.domain.repository.UserRepository as DomainUserRepository

class UserRepository(
    private val userRemoteRepository: DomainUserRepository,
    private val userLocalRepository: DomainUserRepository
) : DomainUserRepository {

    companion object {

        private var instance: UserRepository? = null

        fun getInstance(
            userRemoteRepository: DomainUserRepository,
            userLocalRepository: DomainUserRepository
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
        return userLocalRepository.getUser(username)
            .onErrorResumeNext {
                // if there is no required data redirect call to remote repository
                userRemoteRepository.getUser(username)
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
        return userLocalRepository.getUserRepos(user)
            .onErrorResumeNext {
                // if there is no required data redirect call to remote repository
                userRemoteRepository.getUserRepos(user)
                    .doOnError {
                        Throwable("Problem with getting user repos")
                    }
            }
    }

    override fun saveUser(user: User): Completable {
        return userLocalRepository.saveUser(user)
    }

    override fun deleteUser(user: User): Completable {
        return userLocalRepository.deleteUser(user)
    }
}