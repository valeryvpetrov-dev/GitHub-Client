package ru.geekbrains.android.level3.valeryvpetrov.domain.usecase

import io.reactivex.Single
import ru.geekbrains.android.level3.valeryvpetrov.domain.entity.User
import ru.geekbrains.android.level3.valeryvpetrov.domain.executor.IExecutionScheduler
import ru.geekbrains.android.level3.valeryvpetrov.domain.executor.IPostExecutionScheduler
import ru.geekbrains.android.level3.valeryvpetrov.domain.repository.IUserRepository
import javax.inject.Inject

class GetUserLocalUseCase
@Inject constructor(
    executionScheduler: IExecutionScheduler,
    postExecutionScheduler: IPostExecutionScheduler,
    private val userRepository: IUserRepository
) : UseCase<String, User>(
    executionScheduler,
    postExecutionScheduler
) {

    override fun buildSingle(requestValue: String): Single<User> {
        return userRepository.getUser(requestValue, forceDb = true)
    }
}