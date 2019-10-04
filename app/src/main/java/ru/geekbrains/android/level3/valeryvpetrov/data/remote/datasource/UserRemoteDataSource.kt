package ru.geekbrains.android.level3.valeryvpetrov.data.remote.datasource

import io.reactivex.Completable
import io.reactivex.Single
import retrofit2.Retrofit
import ru.geekbrains.android.level3.valeryvpetrov.data.remote.github.UserApi
import ru.geekbrains.android.level3.valeryvpetrov.data.repository.datasource.IUserDataSource
import ru.geekbrains.android.level3.valeryvpetrov.domain.entity.RepoItem
import ru.geekbrains.android.level3.valeryvpetrov.domain.entity.User
import ru.geekbrains.android.level3.valeryvpetrov.domain.entity.mapper.mapToDomain

class UserRemoteDataSource(
    private val retrofitGithub: Retrofit
) : IUserDataSource {

    private val userApi: UserApi by lazy {
        retrofitGithub.create(UserApi::class.java)
    }

    override fun getUser(username: String): Single<User> {
        return userApi.getUser(username)
            .map { it.mapToDomain() }
    }

    override fun getUserRepos(user: User): Single<List<RepoItem>?> {
        return userApi.getUserRepos(user.login)
            .map { list -> list.map { it.mapToDomain() } }
    }

    override fun saveUser(user: User): Completable {
        return Completable.fromAction {
            Throwable("Remote repository does not support saving function")
        }
    }

    override fun deleteUser(user: User): Completable {
        return Completable.fromAction {
            Throwable("Remote repository does not support delete function")
        }
    }
}