package ru.geekbrains.android.level3.valeryvpetrov.domain.usecase

import io.reactivex.Single
import ru.geekbrains.android.level3.valeryvpetrov.domain.entity.UserItem
import ru.geekbrains.android.level3.valeryvpetrov.domain.executor.IExecutionScheduler
import ru.geekbrains.android.level3.valeryvpetrov.domain.executor.IPostExecutionScheduler
import ru.geekbrains.android.level3.valeryvpetrov.domain.repository.UserRepository

class GetUsersUseCase(
    executionScheduler: IExecutionScheduler,
    postExecutionScheduler: IPostExecutionScheduler,
    private val userRepository: UserRepository
) : UseCase<Unit, List<UserItem>>(
    executionScheduler,
    postExecutionScheduler
) {

    override fun buildSingle(requestValue: Unit): Single<List<UserItem>> {
        return userRepository.getUsers()
    }
}