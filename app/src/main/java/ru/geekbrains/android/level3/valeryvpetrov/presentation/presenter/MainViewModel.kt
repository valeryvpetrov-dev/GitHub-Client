package ru.geekbrains.android.level3.valeryvpetrov.presentation.presenter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.observers.DisposableSingleObserver
import ru.geekbrains.android.level3.valeryvpetrov.domain.entity.RepoItem
import ru.geekbrains.android.level3.valeryvpetrov.domain.entity.User
import ru.geekbrains.android.level3.valeryvpetrov.domain.entity.UserItem
import ru.geekbrains.android.level3.valeryvpetrov.domain.usecase.GetUserReposUseCase
import ru.geekbrains.android.level3.valeryvpetrov.domain.usecase.GetUserUseCase
import ru.geekbrains.android.level3.valeryvpetrov.domain.usecase.GetUsersUseCase
import ru.geekbrains.android.level3.valeryvpetrov.util.ConnectivityManager

class MainViewModel(
    private val connectivityManager: ConnectivityManager,
    private val getUsersUseCase: GetUsersUseCase,
    private val getUserUseCase: GetUserUseCase,
    private val getUserReposUseCase: GetUserReposUseCase
) : ViewModel() {

    private val _userRepos = MutableLiveData<List<RepoItem>>()
    val userRepos: LiveData<List<RepoItem>>
        get() = _userRepos

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

    fun loadUserWithRepos(username: String) {
        if (username.trim().isEmpty()) {
            _loadError.value = Throwable("Pass valid username")
            return
        }

        _dataLoading.value = true

        if (connectivityManager.isNetworkConnected()) {
            getUserReposUseCase.execute(username, UserReposUseCaseSingleObserver())
        } else {
            _dataLoading.value = false
            _loadError.value = Throwable("No internet connection")
        }
    }

    inner class UserReposUseCaseSingleObserver : DisposableSingleObserver<List<RepoItem>>() {

        override fun onSuccess(repoItems: List<RepoItem>) {
            _dataLoading.value = false
            _userRepos.value = repoItems
        }

        override fun onError(e: Throwable) {
            _dataLoading.value = false
            _loadError.value = e
        }
    }
}