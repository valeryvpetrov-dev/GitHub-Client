package ru.geekbrains.android.level3.valeryvpetrov.data

import io.reactivex.Single
import io.reactivex.functions.Function
import ru.geekbrains.android.level3.valeryvpetrov.domain.entity.RepoItem
import ru.geekbrains.android.level3.valeryvpetrov.domain.entity.User
import ru.geekbrains.android.level3.valeryvpetrov.domain.repository.UserRepository as DomainUserRepository

class UserRepository(
    private val userRemoteRepository: DomainUserRepository
) : DomainUserRepository {

    companion object {

        private var instance: UserRepository? = null

        fun getInstance(userRemoteRepository: DomainUserRepository) =
            instance ?: UserRepository(userRemoteRepository).apply { instance = this }
    }

    override fun getUser(username: String): Single<User> {
        return userRemoteRepository.getUser(username)
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

    override fun getUserRepos(user: User): Single<List<RepoItem>> {
        return userRemoteRepository.getUserRepos(user)
            .doOnError {
                Throwable("Problem with getting user repos")
            }
    }
}