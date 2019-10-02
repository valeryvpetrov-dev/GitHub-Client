package ru.geekbrains.android.level3.valeryvpetrov.data.remote

import io.reactivex.Single
import retrofit2.Retrofit
import ru.geekbrains.android.level3.valeryvpetrov.domain.entity.mapper.mapToDomain
import ru.geekbrains.android.level3.valeryvpetrov.domain.entity.RepoItem
import ru.geekbrains.android.level3.valeryvpetrov.domain.entity.User
import ru.geekbrains.android.level3.valeryvpetrov.domain.entity.UserItem
import ru.geekbrains.android.level3.valeryvpetrov.domain.repository.UserRepository as DomainUserRepository

class UserRemoteRepository(
    private val retrofitGithub: Retrofit
) : DomainUserRepository {

    private val userApi: UserApi by lazy {
        retrofitGithub.create(UserApi::class.java)
    }

    override fun getUsers(): Single<List<UserItem>> {
        return userApi.getUsers()
            .map { list -> list.map { it.mapToDomain() } }
    }

    override fun getUser(username: String): Single<User> {
        return userApi.getUser(username)
            .map { it.mapToDomain() }
    }

    /**
     * It is guaranteed that user exists
     */
    override fun getUserRepos(username: String): Single<List<RepoItem>> {
        return userApi.getUserRepos(username)
            .map { list -> list.map { it.mapToDomain() } }
    }
}
