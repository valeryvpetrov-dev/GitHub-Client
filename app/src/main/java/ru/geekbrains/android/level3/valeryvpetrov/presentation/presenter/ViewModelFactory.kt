package ru.geekbrains.android.level3.valeryvpetrov.presentation.presenter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import retrofit2.Retrofit
import ru.geekbrains.android.level3.valeryvpetrov.data.UserRepository
import ru.geekbrains.android.level3.valeryvpetrov.data.remote.UserRemoteRepository
import ru.geekbrains.android.level3.valeryvpetrov.domain.usecase.GetUserUseCase
import ru.geekbrains.android.level3.valeryvpetrov.domain.usecase.GetUsersUseCase
import ru.geekbrains.android.level3.valeryvpetrov.domain.usecase.UseCaseHandler
import ru.geekbrains.android.level3.valeryvpetrov.util.AppExecutors
import ru.geekbrains.android.level3.valeryvpetrov.util.ConnectivityManager

class ViewModelFactory(
    private val connectivityManager: ConnectivityManager,
    private val useCaseHandler: UseCaseHandler,
    private val getUsersUseCase: GetUsersUseCase,
    private val getUserUseCase: GetUserUseCase
) : ViewModelProvider.NewInstanceFactory() {

    companion object {

        private var instance: ViewModelFactory? = null

        fun getInstance(
            connectivityManager: ConnectivityManager,
            appExecutors: AppExecutors,
            retrofitGithub: Retrofit
        ): ViewModelFactory {
            val userRemoteRepository = UserRemoteRepository(appExecutors, retrofitGithub)
            val userRepository = UserRepository.getInstance(userRemoteRepository)

            if (instance == null) {
                instance = ViewModelFactory(
                    connectivityManager,
                    UseCaseHandler.getInstance(appExecutors),
                    GetUsersUseCase(userRepository),
                    GetUserUseCase(userRepository)
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
                        useCaseHandler,
                        getUsersUseCase,
                        getUserUseCase
                    )
                else ->
                    throw IllegalArgumentException("Unknown ViewModel class $modelClass")
            }
        } as T
}