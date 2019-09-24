package ru.geekbrains.android.level3.valeryvpetrov.presentation.presenter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.geekbrains.android.level3.valeryvpetrov.domain.entity.User
import ru.geekbrains.android.level3.valeryvpetrov.domain.usecase.GetUsersUseCase
import ru.geekbrains.android.level3.valeryvpetrov.domain.usecase.UseCase
import ru.geekbrains.android.level3.valeryvpetrov.domain.usecase.UseCaseHandler
import ru.geekbrains.android.level3.valeryvpetrov.util.ConnectivityManager

class MainViewModel(
    private val connectivityManager: ConnectivityManager,
    private val useCaseHandler: UseCaseHandler,
    private val getUsersUseCase: GetUsersUseCase
) : ViewModel() {

    private val _users = MutableLiveData<List<User>>().apply { value = emptyList() }
    val users: LiveData<List<User>>
        get() = _users

    private val _loadError = MutableLiveData<Throwable>()
    val loadError: LiveData<Throwable>
        get() = _loadError

    private val _dataLoading = MutableLiveData<Boolean>()
    val dataLoading: LiveData<Boolean>
        get() = _dataLoading

    fun loadUsers() {
        _dataLoading.value = true

        if (connectivityManager.isNetworkConnected()) {
            useCaseHandler.execute(getUsersUseCase,
                GetUsersUseCase.RequestValue(),
                object : UseCase.Callback<GetUsersUseCase.ResponseValue, GetUsersUseCase.Error> {

                    override fun onSuccess(response: GetUsersUseCase.ResponseValue) {
                        _dataLoading.value = false
                        _users.postValue(response.users)
                    }

                    override fun onError(error: GetUsersUseCase.Error) {
                        _dataLoading.value = false
                        _loadError.postValue(error.throwable)
                    }
                })
        } else {
            _dataLoading.value = false
            _loadError.value = Throwable("No internet connection")
        }
    }
}