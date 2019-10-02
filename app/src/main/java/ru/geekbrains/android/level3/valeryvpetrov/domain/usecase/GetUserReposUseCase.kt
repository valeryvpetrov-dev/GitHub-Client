package ru.geekbrains.android.level3.valeryvpetrov.domain.usecase

import io.reactivex.Single
import ru.geekbrains.android.level3.valeryvpetrov.domain.entity.RepoItem
import ru.geekbrains.android.level3.valeryvpetrov.domain.entity.User
import ru.geekbrains.android.level3.valeryvpetrov.domain.executor.IExecutionScheduler
import ru.geekbrains.android.level3.valeryvpetrov.domain.executor.IPostExecutionScheduler
import ru.geekbrains.android.level3.valeryvpetrov.domain.repository.UserRepository

class GetUserReposUseCase(
    executionScheduler: IExecutionScheduler,
    postExecutionScheduler: IPostExecutionScheduler,
    private val userRepository: UserRepository
) : UseCase<String, List<RepoItem>>(
    executionScheduler,
    postExecutionScheduler
) {

    override fun buildSingle(requestValue: String): Single<List<RepoItem>> {
        return userRepository.getUserRepos(requestValue)
    }
}