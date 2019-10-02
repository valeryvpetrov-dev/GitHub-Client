package ru.geekbrains.android.level3.valeryvpetrov.presentation.presenter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import retrofit2.Retrofit
import ru.geekbrains.android.level3.valeryvpetrov.data.UserRepository
import ru.geekbrains.android.level3.valeryvpetrov.data.remote.UserRemoteRepository
import ru.geekbrains.android.level3.valeryvpetrov.domain.usecase.GetUserReposUseCase
import ru.geekbrains.android.level3.valeryvpetrov.domain.usecase.GetUserUseCase
import ru.geekbrains.android.level3.valeryvpetrov.domain.usecase.GetUsersUseCase
import ru.geekbrains.android.level3.valeryvpetrov.util.AppExecutors
import ru.geekbrains.android.level3.valeryvpetrov.util.ConnectivityManager

class ViewModelFactory(
    private val connectivityManager: ConnectivityManager,
    private val getUsersUseCase: GetUsersUseCase,
    private val getUserUseCase: GetUserUseCase,
    private val getUserReposUseCase: GetUserReposUseCase
) : ViewModelProvider.NewInstanceFactory() {

    companion object {

        private var instance: ViewModelFactory? = null

        fun getInstance(
            connectivityManager: ConnectivityManager,
            appExecutors: AppExecutors,
            retrofitGithub: Retrofit
        ): ViewModelFactory {
            val userRemoteRepository = UserRemoteRepository(retrofitGithub)
            val userRepository = UserRepository.getInstance(userRemoteRepository)
            val networkExecutionScheduler = appExecutors.networkIo
            val mainThreadExecutionScheduler = appExecutors.mainThread

            if (instance == null) {
                instance = ViewModelFactory(
                    connectivityManager,
                    GetUsersUseCase(
                        networkExecutionScheduler,
                        mainThreadExecutionScheduler,
                        userRepository
                    ),
                    GetUserUseCase(
                        networkExecutionScheduler,
                        mainThreadExecutionScheduler,
                        userRepository
                    ),
                    GetUserReposUseCase(
                        networkExecutionScheduler,
                        mainThreadExecutionScheduler,
                        userRepository
                    )
                )
            }
            return instance as ViewModelFactory
        }
    }

    override fun <T : ViewModel?> create(modelClass: Class<T>) =
        with(modelClass) {
            when {
                isAssignableFrom(MainViewModel::class.java) ->
                    MainViewModel(
                        connectivityManager,
                        getUsersUseCase,
                        getUserUseCase,
                        getUserReposUseCase
                    )
                else ->
                    throw IllegalArgumentException("Unknown ViewModel class $modelClass")
            }
        } as T
}