package ru.geekbrains.android.level3.valeryvpetrov.data

import io.reactivex.Single
import ru.geekbrains.android.level3.valeryvpetrov.data.remote.UserRemoteRepository
import ru.geekbrains.android.level3.valeryvpetrov.domain.entity.RepoItem
import ru.geekbrains.android.level3.valeryvpetrov.domain.entity.User
import ru.geekbrains.android.level3.valeryvpetrov.domain.entity.UserItem
import ru.geekbrains.android.level3.valeryvpetrov.domain.repository.UserRepository as DomainUserRepository

class UserRepository(
    private val userRemoteRepository: DomainUserRepository
) : DomainUserRepository {

    companion object {

        private var instance: UserRepository? = null

        fun getInstance(userRemoteRepository: UserRemoteRepository) =
            instance ?: UserRepository(userRemoteRepository).apply { instance = this }
    }

    override fun getUsers(): Single<List<UserItem>> {
        return userRemoteRepository.getUsers()
    }

    override fun getUser(username: String): Single<User> {
        return userRemoteRepository.getUser(username)
    }

    override fun getUserRepos(username: String): Single<List<RepoItem>> {
        return userRemoteRepository.getUserRepos(username)
    }
}