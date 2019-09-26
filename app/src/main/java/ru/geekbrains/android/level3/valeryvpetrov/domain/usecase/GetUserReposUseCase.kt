package ru.geekbrains.android.level3.valeryvpetrov.domain.usecase

import androidx.annotation.WorkerThread
import ru.geekbrains.android.level3.valeryvpetrov.domain.entity.RepoItem
import ru.geekbrains.android.level3.valeryvpetrov.domain.entity.User
import ru.geekbrains.android.level3.valeryvpetrov.domain.repository.UserRepository

class GetUserReposUseCase(
    private val userRepository: UserRepository
) : UseCase<GetUserReposUseCase.RequestValue, GetUserReposUseCase.ResponseValue, GetUserReposUseCase.Error>() {

    class RequestValue(
        val user: User
    ) : UseCase.RequestValue {}

    class ResponseValue(
        val repoItems: List<RepoItem>
    ) : UseCase.ResponseValue {}

    class Error(
        val throwable: Throwable
    ) : UseCase.Error {}

    @WorkerThread
    override fun execute(requestValue: RequestValue) {
        userRepository.getUserRepos(requestValue, object : UserRepository.GetUserReposCallback {

            override fun onSuccess(repoItems: List<RepoItem>) {
                callback.onSuccess(ResponseValue(repoItems))
            }

            override fun onError(throwable: Throwable) {
                callback.onError(Error(throwable))
            }
        })
    }
}