package ru.geekbrains.android.level3.valeryvpetrov.data.remote.datasource

import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.functions.Function
import retrofit2.Retrofit
import ru.geekbrains.android.level3.valeryvpetrov.data.remote.github.UserApi
import ru.geekbrains.android.level3.valeryvpetrov.data.repository.datasource.IUserDataSource
import ru.geekbrains.android.level3.valeryvpetrov.domain.entity.RepoItem
import ru.geekbrains.android.level3.valeryvpetrov.domain.entity.User
import ru.geekbrains.android.level3.valeryvpetrov.domain.entity.mapper.mapToDomain
import javax.inject.Inject

class UserRemoteDataSource
@Inject constructor(
    private val userApi: UserApi
) : IUserDataSource {

    override fun getUser(username: String): Single<User> {
        return userApi.getUser(username)
            .map { it.mapToDomain() }
            .flatMap(Function<User, Single<User>> { user ->
                return@Function getUserRepos(user)
                    .map {
                        user.repoItems = it
                        return@map user
                    }
            })
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
