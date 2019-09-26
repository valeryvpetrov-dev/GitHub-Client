package ru.geekbrains.android.level3.valeryvpetrov.data

import ru.geekbrains.android.level3.valeryvpetrov.data.remote.UserRemoteRepository
import ru.geekbrains.android.level3.valeryvpetrov.domain.usecase.GetUserUseCase
import ru.geekbrains.android.level3.valeryvpetrov.domain.repository.UserRepository as DomainUserRepository

class UserRepository(
    private val userRemoteRepository: DomainUserRepository
) : DomainUserRepository {

    companion object {

        private var instance: UserRepository? = null

        fun getInstance(userRemoteRepository: UserRemoteRepository) =
            instance ?: UserRepository(userRemoteRepository).apply { instance = this }
    }

    override fun getUsers(callback: DomainUserRepository.GetUsersCallback) {
        return userRemoteRepository.getUsers(callback)
    }

    override fun getUser(
        requestValue: GetUserUseCase.RequestValue,
        callback: DomainUserRepository.GetUserCallback
    ) {
        return userRemoteRepository.getUser(requestValue, callback)
    }
}