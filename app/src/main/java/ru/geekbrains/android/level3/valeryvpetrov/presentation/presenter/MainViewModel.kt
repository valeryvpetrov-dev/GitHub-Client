package ru.geekbrains.android.level3.valeryvpetrov.presentation.presenter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import io.reactivex.observers.DisposableSingleObserver
import ru.geekbrains.android.level3.valeryvpetrov.domain.entity.mapper.mapToDomain
import ru.geekbrains.android.level3.valeryvpetrov.domain.usecase.DeleteUserUseCase
import ru.geekbrains.android.level3.valeryvpetrov.domain.usecase.GetUserUseCase
import ru.geekbrains.android.level3.valeryvpetrov.domain.usecase.SaveUserUseCase
import ru.geekbrains.android.level3.valeryvpetrov.presentation.entity.mapper.mapToPresentation
import ru.geekbrains.android.level3.valeryvpetrov.util.ConnectivityManager
import ru.geekbrains.android.level3.valeryvpetrov.domain.entity.User as DomainUser
import ru.geekbrains.android.level3.valeryvpetrov.presentation.entity.User as PresentationUser

class MainViewModel(
    private val connectivityManager: ConnectivityManager,
    private val getUserUseCase: GetUserUseCase,
    private val saveUserUseCase: SaveUserUseCase,
    private val deleteUserUseCase: DeleteUserUseCase
) : ViewModel() {


    private val _user = MutableLiveData<DomainUser>()
    val user: LiveData<PresentationUser?> = Transformations.map(_user) {
        it.mapToPresentation()
    }

    private val _loadError = MutableLiveData<Throwable>()
    val loadError: LiveData<Throwable>
        get() = _loadError

    private val _dataLoading = MutableLiveData<Boolean>()
    val dataLoading: LiveData<Boolean>
        get() = _dataLoading

    private val _saveUserResult = MutableLiveData<Pair<Boolean?, Throwable?>>()
    val saveUserResult: LiveData<Pair<Boolean?, Throwable?>>
        get() = _saveUserResult

    private val _deleteUserResult = MutableLiveData<Pair<Boolean?, Throwable?>>()
    val deleteUserResult: LiveData<Pair<Boolean?, Throwable?>>
        get() = _deleteUserResult

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
        getUserUseCase.execute(username, GetUserUseCaseSingleObserver())
    }

    fun saveUserWithReposToDb(user: PresentationUser?) {
        if (user == null) return

        _dataLoading.value = true
        saveUserUseCase.execute(user.mapToDomain(), SaveUserUseCaseSingleObserver())
    }

    fun deleteUserWithReposFromDb(user: PresentationUser?) {
        if (user == null) return

        _dataLoading.value = true
        deleteUserUseCase.execute(user.mapToDomain(), DeleteUserUseCaseSingleObserver())
    }

    inner class GetUserUseCaseSingleObserver : DisposableSingleObserver<DomainUser>() {

        override fun onSuccess(user: DomainUser) {
            _dataLoading.value = false
            _user.value = user
        }

        override fun onError(e: Throwable) {
            _dataLoading.value = false
            _loadError.value = e
        }
    }

    inner class SaveUserUseCaseSingleObserver : DisposableSingleObserver<Boolean>() {

        override fun onSuccess(result: Boolean) {
            _dataLoading.value = false
            _saveUserResult.value = result to null
        }

        override fun onError(e: Throwable) {
            _dataLoading.value = false
            _saveUserResult.value = null to e
        }
    }

    inner class DeleteUserUseCaseSingleObserver : DisposableSingleObserver<Boolean>() {

        override fun onSuccess(result: Boolean) {
            _dataLoading.value = false
            _deleteUserResult.value = result to null
        }

        override fun onError(e: Throwable) {
            _dataLoading.value = false
            _deleteUserResult.value = null to e
        }
    }
}