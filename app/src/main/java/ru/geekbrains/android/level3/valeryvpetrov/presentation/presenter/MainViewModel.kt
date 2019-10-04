package ru.geekbrains.android.level3.valeryvpetrov.presentation.presenter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import io.reactivex.observers.DisposableSingleObserver
import ru.geekbrains.android.level3.valeryvpetrov.domain.entity.mapper.mapToDomain
import ru.geekbrains.android.level3.valeryvpetrov.domain.usecase.DeleteUserUseCase
import ru.geekbrains.android.level3.valeryvpetrov.domain.usecase.GetUserLocalUseCase
import ru.geekbrains.android.level3.valeryvpetrov.domain.usecase.GetUserRemoteUseCase
import ru.geekbrains.android.level3.valeryvpetrov.domain.usecase.SaveUserUseCase
import ru.geekbrains.android.level3.valeryvpetrov.presentation.entity.mapper.mapToPresentation
import ru.geekbrains.android.level3.valeryvpetrov.util.ConnectivityManager
import ru.geekbrains.android.level3.valeryvpetrov.domain.entity.User as DomainUser
import ru.geekbrains.android.level3.valeryvpetrov.presentation.entity.User as PresentationUser

class MainViewModel(
    private val connectivityManager: ConnectivityManager,
    private val getUserRemoteUseCase: GetUserRemoteUseCase,
    private val getUserLocalUseCase: GetUserLocalUseCase,
    private val saveUserUseCase: SaveUserUseCase,
    private val deleteUserUseCase: DeleteUserUseCase
) : ViewModel() {

    private val _user = MutableLiveData<DomainUser?>()
    val user: LiveData<PresentationUser?> = Transformations.map(_user) {
        it?.mapToPresentation()
    }

    private val _getUserRemoteResult =
        MutableLiveData<UseCaseResult<PresentationUser, Event<Throwable>>>()
    val getUserRemoteResult: LiveData<UseCaseResult<PresentationUser, Event<Throwable>>> =
        _getUserRemoteResult

    private val _getUserLocalResult =
        MutableLiveData<UseCaseResult<PresentationUser, Event<Throwable>>>()
    val getUserLocalResult: LiveData<UseCaseResult<PresentationUser, Event<Throwable>>> =
        _getUserLocalResult

    private val _saveUserResult = MutableLiveData<UseCaseResult<Event<Boolean>, Event<Throwable>>>()
    val saveUserResult: LiveData<UseCaseResult<Event<Boolean>, Event<Throwable>>>
        get() = _saveUserResult

    private val _deleteUserResult =
        MutableLiveData<UseCaseResult<Event<Boolean>, Event<Throwable>>>()
    val deleteUserResult: LiveData<UseCaseResult<Event<Boolean>, Event<Throwable>>>
        get() = _deleteUserResult

    fun loadUserWithReposFromNetwork(username: String) {
        if (username.trim().isEmpty()) {
            _getUserRemoteResult.value =
                UseCaseResult.Error(Event(Throwable("Pass valid username")))
            return
        }
        _getUserRemoteResult.value = UseCaseResult.Loading()
        if (connectivityManager.isNetworkConnected()) {
            getUserRemoteUseCase.execute(username, GetUserRemoteUseCaseSingleObserver())
        } else {
            _getUserRemoteResult.value =
                UseCaseResult.Error(Event(Throwable("No internet connection")))
        }
    }

    fun loadUserWithReposFromDb(username: String) {
        if (username.trim().isEmpty()) {
            _getUserLocalResult.value =
                UseCaseResult.Error(Event(Throwable("Pass valid username")))
            return
        }
        _getUserLocalResult.value = UseCaseResult.Loading()
        getUserLocalUseCase.execute(username, GetUserLocalUseCaseSingleObserver())
    }

    fun saveUserWithReposToDb(user: PresentationUser) {
        _saveUserResult.value = UseCaseResult.Loading()
        saveUserUseCase.execute(user.mapToDomain(), SaveUserUseCaseSingleObserver())
    }

    fun deleteUserWithReposFromDb(user: PresentationUser) {
        _deleteUserResult.value = UseCaseResult.Loading()
        deleteUserUseCase.execute(user.mapToDomain(), DeleteUserUseCaseSingleObserver())
    }

    inner class GetUserRemoteUseCaseSingleObserver : DisposableSingleObserver<DomainUser>() {

        override fun onSuccess(user: DomainUser) {
            _user.value = user
            _getUserRemoteResult.value = UseCaseResult.Success(user.mapToPresentation())
        }

        override fun onError(e: Throwable) {
            _getUserRemoteResult.value = UseCaseResult.Error(Event(e))
        }
    }

    inner class GetUserLocalUseCaseSingleObserver : DisposableSingleObserver<DomainUser>() {

        override fun onSuccess(user: DomainUser) {
            _user.value = user
            _getUserLocalResult.value = UseCaseResult.Success(user.mapToPresentation())
        }

        override fun onError(e: Throwable) {
            _getUserLocalResult.value = UseCaseResult.Error(Event(e))
        }
    }

    inner class SaveUserUseCaseSingleObserver : DisposableSingleObserver<Boolean>() {

        override fun onSuccess(result: Boolean) {
            _saveUserResult.value = UseCaseResult.Success(Event(result))
        }

        override fun onError(e: Throwable) {
            _saveUserResult.value = UseCaseResult.Error(Event(e))
        }
    }

    inner class DeleteUserUseCaseSingleObserver : DisposableSingleObserver<Boolean>() {

        override fun onSuccess(result: Boolean) {
            _user.value = null
            _deleteUserResult.value = UseCaseResult.Success(Event(result))
        }

        override fun onError(e: Throwable) {
            _deleteUserResult.value = UseCaseResult.Error(Event(e))
        }
    }
}