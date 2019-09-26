package ru.geekbrains.android.level3.valeryvpetrov.presentation.presenter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.geekbrains.android.level3.valeryvpetrov.domain.entity.User
import ru.geekbrains.android.level3.valeryvpetrov.domain.entity.UserItem
import ru.geekbrains.android.level3.valeryvpetrov.domain.usecase.GetUserUseCase
import ru.geekbrains.android.level3.valeryvpetrov.domain.usecase.GetUsersUseCase
import ru.geekbrains.android.level3.valeryvpetrov.domain.usecase.UseCase
import ru.geekbrains.android.level3.valeryvpetrov.domain.usecase.UseCaseHandler
import ru.geekbrains.android.level3.valeryvpetrov.util.ConnectivityManager

class MainViewModel(
    private val connectivityManager: ConnectivityManager,
    private val useCaseHandler: UseCaseHandler,
    private val getUsersUseCase: GetUsersUseCase,
    private val getUserUseCase: GetUserUseCase
) : ViewModel() {

    private val _user = MutableLiveData<User>()
    val user: LiveData<User>
        get() = _user

    private val _users = MutableLiveData<List<UserItem>>().apply { value = emptyList() }
    val users: LiveData<List<UserItem>>
        get() = _users

    private val _loadError = MutableLiveData<Throwable>()
    val loadError: LiveData<Throwable>
        get() = _loadError

    private val _dataLoading = MutableLiveData<Boolean>()
    val dataLoading: LiveData<Boolean>
        get() = _dataLoading

    fun loadUser(username: String) {
        if (username.trim().isEmpty()) {
            _loadError.value = Throwable("Pass valid username")
            return
        }

        _dataLoading.value = true

        if (connectivityManager.isNetworkConnected()) {
            useCaseHandler.execute(getUserUseCase,
                GetUserUseCase.RequestValue(username),
                object : UseCase.Callback<GetUserUseCase.ResponseValue, GetUserUseCase.Error> {

                    override fun onSuccess(response: GetUserUseCase.ResponseValue) {
                        _dataLoading.value = false
                        _user.postValue(response.user)
                    }

                    override fun onError(error: GetUserUseCase.Error) {
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