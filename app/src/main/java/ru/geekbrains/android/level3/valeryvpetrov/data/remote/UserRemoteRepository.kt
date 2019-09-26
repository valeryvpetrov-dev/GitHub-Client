package ru.geekbrains.android.level3.valeryvpetrov.data.remote

import androidx.annotation.WorkerThread
import retrofit2.Retrofit
import ru.geekbrains.android.level3.valeryvpetrov.data.remote.entity.mapToDomain
import ru.geekbrains.android.level3.valeryvpetrov.domain.usecase.GetUserReposUseCase
import ru.geekbrains.android.level3.valeryvpetrov.domain.usecase.GetUserUseCase
import ru.geekbrains.android.level3.valeryvpetrov.util.AppExecutors
import ru.geekbrains.android.level3.valeryvpetrov.domain.repository.UserRepository as DomainUserRepository

class UserRemoteRepository(
    private val appExecutors: AppExecutors,
    private val retrofitGithub: Retrofit
) : DomainUserRepository {

    private val userApi: UserApi by lazy {
        retrofitGithub.create(UserApi::class.java)
    }

    @WorkerThread
    override fun getUsers(callback: DomainUserRepository.GetUsersCallback) {
        val runnable = Runnable {
            val response = userApi.getUsers().execute()

            if (response.isSuccessful) {
                val data = response.body()
                if (data != null) {
                    val users = data.map { it.mapToDomain() }
                    appExecutors.mainThread.execute {
                        callback.onSuccess(users)
                    }
                } else {
                    appExecutors.mainThread.execute {
                        callback.onError(Throwable("Empty response body"))
                    }
                }
            } else {
                appExecutors.mainThread.execute {
                    callback.onError(Throwable("Error code: ${response.code()}"))
                }
            }
        }

        appExecutors.networkIo.execute(runnable)
    }

    override fun getUser(
        requestValue: GetUserUseCase.RequestValue,
        callback: DomainUserRepository.GetUserCallback
    ) {
        val runnable = Runnable {
            val response = userApi.getUser(requestValue.username).execute()

            if (response.isSuccessful) {
                val data = response.body()
                if (data != null) {
                    val user = data.mapToDomain()
                    appExecutors.mainThread.execute {
                        callback.onSuccess(user)
                    }
                } else {
                    appExecutors.mainThread.execute {
                        callback.onError(Throwable("Empty response body"))
                    }
                }
            } else {
                appExecutors.mainThread.execute {
                    callback.onError(Throwable("Error code: ${response.code()}"))
                }
            }
        }

        appExecutors.networkIo.execute(runnable)
    }

    /**
     * It is guaranteed that user exists
     */
    override fun getUserRepos(
        requestValue: GetUserReposUseCase.RequestValue,
        callback: DomainUserRepository.GetUserReposCallback
    ) {
        val runnable = Runnable {
            val username = requestValue.user.login
            val response = userApi.getUserRepos(username).execute()

            if (response.isSuccessful) {
                val data = response.body()
                if (data != null) {
                    val userRepos = data.map { it.mapToDomain() }
                    appExecutors.mainThread.execute {
                        callback.onSuccess(userRepos)
                    }
                } else {
                    appExecutors.mainThread.execute {
                        callback.onSuccess(emptyList())
                    }
                }
            } else {
                appExecutors.mainThread.execute {
                    callback.onError(Throwable("Error code: ${response.code()}"))
                }
            }
        }

        appExecutors.networkIo.execute(runnable)
    }
}
