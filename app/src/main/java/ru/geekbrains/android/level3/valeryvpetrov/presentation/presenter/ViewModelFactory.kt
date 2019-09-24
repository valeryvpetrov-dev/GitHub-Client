package ru.geekbrains.android.level3.valeryvpetrov.presentation.presenter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import retrofit2.Retrofit
import ru.geekbrains.android.level3.valeryvpetrov.data.UserRepository
import ru.geekbrains.android.level3.valeryvpetrov.data.network.remote.UserRemoteRepository
import ru.geekbrains.android.level3.valeryvpetrov.domain.usecase.GetUsersUseCase
import ru.geekbrains.android.level3.valeryvpetrov.domain.usecase.UseCaseHandler
import ru.geekbrains.android.level3.valeryvpetrov.util.AppExecutors
import ru.geekbrains.android.level3.valeryvpetrov.util.ConnectivityManager

class ViewModelFactory(
    private val connectivityManager: ConnectivityManager,
    private val useCaseHandler: UseCaseHandler,
    private val getUsersUseCase: GetUsersUseCase
) : ViewModelProvider.NewInstanceFactory() {

    companion object {

        private var instance: ViewModelFactory? = null

        fun getInstance(
            connectivityManager: ConnectivityManager,
            appExecutors: AppExecutors,
            retrofitGithub: Retrofit
        ) =
            instance ?: ViewModelFactory(
                connectivityManager,
                UseCaseHandler.getInstance(appExecutors),
                GetUsersUseCase(
                    UserRepository.getInstance(
                        UserRemoteRepository(appExecutors, retrofitGithub)
                    )
                )
            ).apply { instance = this }
    }

    override fun <T : ViewModel?> create(modelClass: Class<T>) =
        with(modelClass) {
            when {
                isAssignableFrom(MainViewModel::class.java) ->
                    MainViewModel(connectivityManager, useCaseHandler, getUsersUseCase)
                else ->
                    throw IllegalArgumentException("Unknown ViewModel class $modelClass")
            }
        } as T
}