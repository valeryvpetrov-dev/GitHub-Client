package ru.geekbrains.android.level3.valeryvpetrov.domain.usecase

import io.reactivex.Single
import ru.geekbrains.android.level3.valeryvpetrov.domain.entity.User
import ru.geekbrains.android.level3.valeryvpetrov.domain.executor.IExecutionScheduler
import ru.geekbrains.android.level3.valeryvpetrov.domain.executor.IPostExecutionScheduler
import ru.geekbrains.android.level3.valeryvpetrov.domain.repository.UserRepository

class GetUserUseCase(
    executionScheduler: IExecutionScheduler,
    postExecutionScheduler: IPostExecutionScheduler,
    private val userRepository: UserRepository
) : UseCase<String, User>(
    executionScheduler,
    postExecutionScheduler
) {

    override fun buildSingle(requestValue: String): Single<User> {
        return userRepository.getUser(requestValue)
    }
}