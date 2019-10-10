package ru.geekbrains.android.level3.valeryvpetrov.data.repository

import io.reactivex.Completable
import io.reactivex.Single
import ru.geekbrains.android.level3.valeryvpetrov.data.repository.datasource.IUserDataSource
import ru.geekbrains.android.level3.valeryvpetrov.domain.entity.RepoItem
import ru.geekbrains.android.level3.valeryvpetrov.domain.entity.User
import javax.inject.Inject
import ru.geekbrains.android.level3.valeryvpetrov.domain.repository.IUserRepository as DomainUserRepository

class UserRepository
@Inject constructor(
    private val userRemoteDataSource: IUserDataSource,
    private val userLocalDataSource: IUserDataSource
) : DomainUserRepository {

    override fun getUser(
        username: String,
        forceNetwork: Boolean,
        forceDb: Boolean
    ): Single<User> {
        return when {
            username.isEmpty() -> Single.error(Throwable("Empty username"))
            forceNetwork -> userRemoteDataSource.getUser(username)
            forceDb -> userLocalDataSource.getUser(username)
            else -> {
                // first try to get from local repository
                userLocalDataSource.getUser(username)
                    .onErrorResumeNext {
                        // if there is no required data redirect call to remote repository
                        userRemoteDataSource.getUser(username)
                            .doOnError {
                                Throwable("Problem with getting user")
                            }
                    }
            }
        }
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