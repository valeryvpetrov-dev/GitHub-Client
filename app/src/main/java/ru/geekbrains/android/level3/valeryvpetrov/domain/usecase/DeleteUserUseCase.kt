package ru.geekbrains.android.level3.valeryvpetrov.domain.usecase

import io.reactivex.Single
import ru.geekbrains.android.level3.valeryvpetrov.domain.entity.User
import ru.geekbrains.android.level3.valeryvpetrov.domain.executor.IExecutionScheduler
import ru.geekbrains.android.level3.valeryvpetrov.domain.executor.IPostExecutionScheduler
import ru.geekbrains.android.level3.valeryvpetrov.domain.repository.UserRepository

class DeleteUserUseCase(
    executionScheduler: IExecutionScheduler,
    postExecutionScheduler: IPostExecutionScheduler,
    private val userRepository: UserRepository
) : UseCase<User, Boolean>(
    executionScheduler, postExecutionScheduler
) {

    override fun buildSingle(requestValue: User): Single<Boolean> {
        return userRepository.deleteUser(requestValue)
            .toSingle { true }
    }
}