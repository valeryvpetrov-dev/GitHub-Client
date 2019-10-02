package ru.geekbrains.android.level3.valeryvpetrov.presentation.presenter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.observers.DisposableSingleObserver
import ru.geekbrains.android.level3.valeryvpetrov.domain.usecase.GetUserUseCase
import ru.geekbrains.android.level3.valeryvpetrov.presentation.entity.RepoItem
import ru.geekbrains.android.level3.valeryvpetrov.presentation.entity.User
import ru.geekbrains.android.level3.valeryvpetrov.presentation.entity.mapper.mapToPresentation
import ru.geekbrains.android.level3.valeryvpetrov.util.ConnectivityManager
import ru.geekbrains.android.level3.valeryvpetrov.domain.entity.User as DomainUser

class MainViewModel(
    private val connectivityManager: ConnectivityManager,
    private val getUserUseCase: GetUserUseCase
) : ViewModel() {

    private val _user = MutableLiveData<User>()
    val user: LiveData<User>
        get() = _user

    private val _loadError = MutableLiveData<Throwable>()
    val loadError: LiveData<Throwable>
        get() = _loadError

    private val _dataLoading = MutableLiveData<Boolean>()
    val dataLoading: LiveData<Boolean>
        get() = _dataLoading

    fun loadUserWithReposFromNetwork(username: String) {
        if (username.trim().isEmpty()) {
            _loadError.value = Throwable("Pass valid username")
            return
        }

        _dataLoading.value = true

        if (connectivityManager.isNetworkConnected()) {
            getUserUseCase.execute(username, GetUserUseCaseSingleObserver())
        } else {
            _dataLoading.value = false
            _loadError.value = Throwable("No internet connection")
        }
    }

    fun loadUserWithReposFromDb(username: String) {
        if (username.trim().isEmpty()) {
            _loadError.value = Throwable("Pass valid username")
            return
        }

        _dataLoading.value = true
        TODO()
    }

    fun saveUserWithReposToDb(user: User?) {
        if (user == null) return
        TODO()
    }

    fun deleteUserWithReposFromDb(user: User?) {
        if (user == null) return
        TODO()
    }

    inner class GetUserUseCaseSingleObserver : DisposableSingleObserver<DomainUser>() {

        override fun onSuccess(user: DomainUser) {
            _dataLoading.value = false
            _user.value = user.mapToPresentation()
        }

        override fun onError(e: Throwable) {
            _dataLoading.value = false
            _loadError.value = e
        }
    }
}