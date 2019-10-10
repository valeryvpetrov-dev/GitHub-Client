package ru.geekbrains.android.level3.valeryvpetrov

import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.observers.TestObserver
import org.junit.After
import org.junit.BeforeClass
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import ru.geekbrains.android.level3.valeryvpetrov.data.repository.UserRepository
import ru.geekbrains.android.level3.valeryvpetrov.data.repository.datasource.IUserDataSource
import ru.geekbrains.android.level3.valeryvpetrov.domain.entity.RepoItem
import ru.geekbrains.android.level3.valeryvpetrov.domain.entity.User
import ru.geekbrains.android.level3.valeryvpetrov.domain.repository.IUserRepository

class UserRepositoryTest {

    companion object {

        const val emptyUsername = ""
        const val validUsername = "android10"

        val emptyUsernameError = Throwable("Empty username")
        val userNullReposError = Throwable("Problem with getting user repos")
        val userAlreadyExistsError =
            Throwable("There is already exists user with login $validUsername in database")
        val userDoesNotExistError =
            Throwable("There is no user with login $validUsername to delete")

        private lateinit var validUserEmptyRepos: User
        private lateinit var validUserNullRepos: User
        private lateinit var validUserWithRepos: User
        private lateinit var validUserRepos: List<RepoItem>

        private lateinit var userRepository: IUserRepository
        private var getUserObserver: TestObserver<User>? = null
        private var getUserReposObserver: TestObserver<List<RepoItem>?>? = null
        private var saveUser: TestObserver<Void>? = null
        private var deleteUser: TestObserver<Void>? = null

        @BeforeClass
        @JvmStatic
        fun beforeClass() {
            validUserEmptyRepos =
                User(1, validUsername, null, null, null, null, null, null, listOf())
            validUserRepos = listOf(
                RepoItem(1, "fullName1", null),
                RepoItem(2, "fullName2", null)
            )
            validUserWithRepos =
                User(2, validUsername, null, null, null, null, null, null, validUserRepos)
            validUserNullRepos = User(3, validUsername, null, null, null, null, null, null, null)

            val userRemoteDataSource = Mockito.mock(IUserDataSource::class.java)
            val userLocalDataSource = Mockito.mock(IUserDataSource::class.java)

            emptyUsername.run {
                val returnValue: Single<User> = Single.error(emptyUsernameError)
                `when`(userRemoteDataSource.getUser(this)).thenReturn(returnValue)
                `when`(userLocalDataSource.getUser(this)).thenReturn(returnValue)
            }
            validUsername.run {
                val returnValue: Single<User> = Single.just(validUserEmptyRepos)
                `when`(userRemoteDataSource.getUser(this)).thenReturn(returnValue)
                `when`(userLocalDataSource.getUser(this)).thenReturn(returnValue)
            }
            validUserEmptyRepos.run {
                val returnValue: Single<List<RepoItem>?> = Single.just(listOf())
                `when`(userRemoteDataSource.getUserRepos(validUserEmptyRepos)).thenReturn(
                    returnValue
                )
                `when`(userLocalDataSource.getUserRepos(validUserEmptyRepos)).thenReturn(returnValue)
            }
            userNullReposError.run {
                val returnValue: Single<List<RepoItem>?> = Single.error(userNullReposError)
                `when`(userRemoteDataSource.getUserRepos(validUserNullRepos)).thenReturn(returnValue)
                `when`(userLocalDataSource.getUserRepos(validUserNullRepos)).thenReturn(returnValue)
            }
            validUserWithRepos.run {
                `when`(userRemoteDataSource.getUserRepos(this)).thenReturn(
                    Single.just(
                        validUserRepos
                    )
                )
                `when`(userLocalDataSource.getUserRepos(this)).thenReturn(Single.just(validUserRepos))
                `when`(userLocalDataSource.saveUser(this)).thenReturn(
                    Completable.complete(),
                    Completable.error(userAlreadyExistsError)
                )
                `when`(userLocalDataSource.deleteUser(this)).thenReturn(
                    Completable.complete(),
                    Completable.error(userDoesNotExistError)
                )
            }

            userRepository = UserRepository(userRemoteDataSource, userLocalDataSource)
        }
    }

    @After
    fun after() {
        getUserObserver?.dispose()
        getUserReposObserver?.dispose()
        saveUser?.dispose()
        deleteUser?.dispose()
    }

    @Test
    fun getUser_EmptyUsernameForceNetwork_Throwable() {
        getUserObserver = userRepository.getUser(emptyUsername, forceNetwork = true).test()

        getUserObserver?.run {
            assertError(emptyUsernameError::class.java)
            assertErrorMessage(emptyUsernameError.message)
        }
    }

    @Test
    fun getUser_EmptyUsernameForceDb_Throwable() {
        getUserObserver = userRepository.getUser(emptyUsername, forceDb = true).test()

        getUserObserver?.run {
            assertError(emptyUsernameError::class.java)
            assertErrorMessage(emptyUsernameError.message)
        }
    }

    @Test
    fun getUser_EmptyUsernameNoForce_Throwable() {
        getUserObserver = userRepository.getUser(emptyUsername).test()

        getUserObserver?.run {
            assertError(emptyUsernameError::class.java)
            assertErrorMessage(emptyUsernameError.message)
        }
    }

    @Test
    fun getUser_EmptyUsernameForceNetworkAndDb_Throwable() {
        getUserObserver =
            userRepository.getUser(emptyUsername, forceNetwork = true, forceDb = true).test()

        getUserObserver?.run {
            assertError(emptyUsernameError::class.java)
            assertErrorMessage(emptyUsernameError.message)
        }
    }

    @Test
    fun getUser_ValidUsernameForceNetwork_User() {
        getUserObserver = userRepository.getUser(validUsername, forceNetwork = true).test()

        getUserObserver?.run {
            assertValue(validUserEmptyRepos)
        }
    }

    @Test
    fun getUser_ValidUsernameForceDb_User() {
        getUserObserver = userRepository.getUser(validUsername, forceDb = true).test()

        getUserObserver?.run {
            assertValue(validUserEmptyRepos)
        }
    }

    @Test
    fun getUser_ValidUsernameNoForce_User() {
        getUserObserver = userRepository.getUser(validUsername).test()

        getUserObserver?.run {
            assertValue(validUserEmptyRepos)
        }
    }

    @Test
    fun getUser_ValidUsernameForceNetworkAndDb_User() {
        getUserObserver =
            userRepository.getUser(validUsername, forceNetwork = true, forceDb = true).test()

        getUserObserver?.run {
            assertValue(validUserEmptyRepos)
        }
    }

    @Test
    fun getUserRepos_ValidUser_ListRepos() {
        getUserReposObserver = userRepository.getUserRepos(validUserWithRepos).test()

        getUserReposObserver?.run {
            assertValue(validUserRepos)
            dispose()
        }
    }

    @Test
    fun getUserRepos_ValidUser_EmptyListRepos() {
        getUserReposObserver = userRepository.getUserRepos(validUserEmptyRepos).test()

        getUserReposObserver?.run {
            assertValue(listOf())
        }
    }

    @Test
    fun getUserRepos_ValidUser_NullListRepos() {
        getUserReposObserver = userRepository.getUserRepos(validUserNullRepos).test()

        getUserReposObserver?.run {
            assertError(userNullReposError::class.java)
            assertErrorMessage(userNullReposError.message)
        }
    }

    @Test
    fun saveUser_ValidUser_UserExists() {
        saveUser = userRepository.saveUser(validUserWithRepos).test()

        saveUser?.run {
            assertError(userAlreadyExistsError::class.java)
            assertErrorMessage(userAlreadyExistsError.message)
        }
    }

    @Test
    fun saveUser_ValidUser_UserDoesNotExist() {
        saveUser = userRepository.saveUser(validUserWithRepos).test()

        saveUser?.run {
            assertComplete()
        }
    }

    @Test
    fun deleteUser_ValidUser_UserExists() {
        deleteUser = userRepository.deleteUser(validUserWithRepos).test()

        deleteUser?.run {
            assertComplete()
        }
    }

    @Test
    fun deleteUser_ValidUser_UserDoesNotExist() {
        deleteUser = userRepository.deleteUser(validUserWithRepos).test()

        deleteUser?.run {
            assertError(userDoesNotExistError::class.java)
            assertErrorMessage(userDoesNotExistError.message)
        }
    }
}