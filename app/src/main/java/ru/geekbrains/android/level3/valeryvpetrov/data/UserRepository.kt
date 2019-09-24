package ru.geekbrains.android.level3.valeryvpetrov.data

import androidx.annotation.WorkerThread
import ru.geekbrains.android.level3.valeryvpetrov.data.network.remote.UserRemoteRepository
import ru.geekbrains.android.level3.valeryvpetrov.domain.repository.UserRepository as DomainUserRepository

class UserRepository(
    private val userRemoteRepository: DomainUserRepository
) : DomainUserRepository {

    companion object {

        private var instance: UserRepository? = null

        fun getInstance(userRemoteRepository: UserRemoteRepository) =
            instance ?: UserRepository(userRemoteRepository).apply { instance = this }
    }

    @WorkerThread
    override fun getUsers(callback: DomainUserRepository.GetUsersCallback) {
        return userRemoteRepository.getUsers(callback)
    }
}