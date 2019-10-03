package ru.geekbrains.android.level3.valeryvpetrov.data.local.repository

import android.util.Log
import io.reactivex.Completable
import io.reactivex.Single
import io.realm.Realm
import ru.geekbrains.android.level3.valeryvpetrov.data.local.realm.entity.mapper.mapToRealm
import ru.geekbrains.android.level3.valeryvpetrov.domain.entity.RepoItem
import ru.geekbrains.android.level3.valeryvpetrov.domain.entity.User
import ru.geekbrains.android.level3.valeryvpetrov.domain.entity.mapper.mapToDomain
import ru.geekbrains.android.level3.valeryvpetrov.util.measureTimeMillis
import ru.geekbrains.android.level3.valeryvpetrov.data.local.realm.entity.User as RealmUser
import ru.geekbrains.android.level3.valeryvpetrov.domain.repository.UserRepository as DomainUserRepository

class UserLocalRepository(
) : DomainUserRepository {

    companion object {

        const val LOG_TAG_EXECUTION_TIME = "ExecutionTime"

        const val LOG_MESSAGE_TEMPLATE_EXECUTION_TIME = "%-10s :: %-20s :: %.5f secs"
    }

    override fun getUser(username: String): Single<User> {
        val singleRealm = Single.create<User> {
            val block: () -> User? = {
                val realm = Realm.getDefaultInstance()

                realm.where(RealmUser::class.java)
                    .equalTo("login", username)
                    .findFirst()
                    ?.mapToDomain()
            }
            val user = measureTimeMillis(block, { time ->
                Log.i(
                    LOG_TAG_EXECUTION_TIME,
                    LOG_MESSAGE_TEMPLATE_EXECUTION_TIME.format("Realm", "getUser", time)
                )
            })

            if (user != null) {
                it.onSuccess(user)
            } else {
                it.onError(Throwable("There is no user with login $username in Realm"))
            }
        }

        return Single.ambArray(singleRealm)
    }

    override fun getUserRepos(user: User): Single<List<RepoItem>?> {
        val singleRealm = Single.create<List<RepoItem>> {
            val block: () -> List<RepoItem>? = {
                val realm = Realm.getDefaultInstance()

                realm.where(RealmUser::class.java)
                    .equalTo("login", user.login)
                    .findFirst()
                    ?.repoItems?.toList()
                    ?.map { items -> items.mapToDomain() }
            }
            val repoItems = measureTimeMillis(block, { time ->
                Log.i(
                    LOG_TAG_EXECUTION_TIME,
                    LOG_MESSAGE_TEMPLATE_EXECUTION_TIME.format("Realm", "getUserRepos", time)
                )
            })

            if (repoItems != null) {
                it.onSuccess(repoItems)
            } else {
                it.onError(Throwable("There is no repos ${user.login} owns in Realm"))
            }
        }

        return Single.ambArray(singleRealm)
    }

    override fun saveUser(user: User): Completable {
        val completableRealm = Completable.fromAction {
            val block: () -> Unit = {
                val realmUser = user.mapToRealm()

                val realm = Realm.getDefaultInstance()

                realm.executeTransaction {
                    it.copyToRealmOrUpdate(realmUser)
                }
            }
            measureTimeMillis(block, { time ->
                Log.i(
                    LOG_TAG_EXECUTION_TIME,
                    LOG_MESSAGE_TEMPLATE_EXECUTION_TIME.format("Realm", "saveUser", time)
                )
            })
        }

        return Completable.concatArray(completableRealm)
    }

    override fun deleteUser(user: User): Completable {

        val completableRealm = Completable.fromAction {
            val block: () -> Unit = {
                val realmUser = user.mapToRealm()

                val realm = Realm.getDefaultInstance()

                realm.executeTransaction {
                    it.where(RealmUser::class.java)
                        .equalTo("id", realmUser.id)
                        .findFirst()
                        ?.deleteFromRealm()
                }
            }
            measureTimeMillis(block, { time ->
                Log.i(
                    LOG_TAG_EXECUTION_TIME,
                    LOG_MESSAGE_TEMPLATE_EXECUTION_TIME.format("Realm", "deleteUser", time)
                )
            })

        }

        return Completable.concatArray(completableRealm)
    }
}
