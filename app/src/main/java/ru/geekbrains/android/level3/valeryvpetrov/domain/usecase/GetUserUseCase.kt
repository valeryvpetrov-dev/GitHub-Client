package ru.geekbrains.android.level3.valeryvpetrov.domain.usecase

import androidx.annotation.WorkerThread
import ru.geekbrains.android.level3.valeryvpetrov.domain.entity.User
import ru.geekbrains.android.level3.valeryvpetrov.domain.entity.UserItem
import ru.geekbrains.android.level3.valeryvpetrov.domain.repository.UserRepository

class GetUserUseCase(
    private val userRepository: UserRepository
) : UseCase<GetUserUseCase.RequestValue, GetUserUseCase.ResponseValue, GetUserUseCase.Error>() {

    class RequestValue(
        val username: String
    ) : UseCase.RequestValue {}

    class ResponseValue(
        val user: User
    ) : UseCase.ResponseValue {}

    class Error(
        val throwable: Throwable
    ) : UseCase.Error {}

    @WorkerThread
    override fun execute(requestValue: RequestValue) {
        userRepository.getUser(requestValue, object : UserRepository.GetUserCallback {

            override fun onSuccess(user: User) {
                callback.onSuccess(ResponseValue(user))
            }

            override fun onError(throwable: Throwable) {
                callback.onError(Error(throwable))
            }
        })
    }
}