package ru.geekbrains.android.level3.valeryvpetrov.domain.usecase

import androidx.annotation.WorkerThread
import ru.geekbrains.android.level3.valeryvpetrov.domain.entity.UserItem
import ru.geekbrains.android.level3.valeryvpetrov.domain.repository.UserRepository

class GetUsersUseCase(
    private val userRepository: UserRepository
) : UseCase<GetUsersUseCase.RequestValue, GetUsersUseCase.ResponseValue, GetUsersUseCase.Error>() {

    class RequestValue : UseCase.RequestValue {}

    class ResponseValue(
        val userItems: List<UserItem>
    ) : UseCase.ResponseValue {}

    class Error(
        val throwable: Throwable
    ) : UseCase.Error {}

    @WorkerThread
    override fun execute(requestValue: RequestValue) {
        userRepository.getUsers(object : UserRepository.GetUsersCallback {

            override fun onSuccess(userItems: List<UserItem>) {
                callback.onSuccess(ResponseValue(userItems))
            }

            override fun onError(throwable: Throwable) {
                callback.onError(Error(throwable))
            }
        })
    }
}