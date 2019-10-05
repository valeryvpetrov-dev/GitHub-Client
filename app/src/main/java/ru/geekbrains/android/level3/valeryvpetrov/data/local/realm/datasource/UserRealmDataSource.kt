package ru.geekbrains.android.level3.valeryvpetrov.data.local.realm.datasource

import io.reactivex.Completable
import io.reactivex.Single
import io.realm.Realm
import ru.geekbrains.android.level3.valeryvpetrov.data.local.realm.entity.mapper.mapToRealm
import ru.geekbrains.android.level3.valeryvpetrov.data.repository.datasource.IUserDataSource
import ru.geekbrains.android.level3.valeryvpetrov.data.util.logcatInfo
import ru.geekbrains.android.level3.valeryvpetrov.data.util.measureTimeInSeconds
import ru.geekbrains.android.level3.valeryvpetrov.domain.entity.RepoItem
import ru.geekbrains.android.level3.valeryvpetrov.domain.entity.User
import ru.geekbrains.android.level3.valeryvpetrov.domain.entity.mapper.mapToDomain
import ru.geekbrains.android.level3.valeryvpetrov.data.local.realm.entity.User as RealmUser

class UserRealmDataSource() : IUserDataSource {

    companion object {

        const val LOG_MESSAGE_NAMESPACE = "Realm"
    }

    override fun getUser(username: String): Single<User> {
        val block: () -> Single<User> = {
            Single.create {
                val realm = Realm.getDefaultInstance()

                val user = realm.where(RealmUser::class.java)
                    .equalTo("login", username)
                    .findFirst()
                    ?.mapToDomain()

                if (user != null) {
                    it.onSuccess(user)
                } else {
                    it.onError(Throwable("There is no user with login $username in Realm"))
                }
            }
        }

        val single =
            measureTimeInSeconds(block, LOG_MESSAGE_NAMESPACE, "getUser", logcatInfo)
        return single
    }

    override fun getUserRepos(user: User): Single<List<RepoItem>?> {
        val block: () -> Single<List<RepoItem>?> = {
            Single.create {
                val realm = Realm.getDefaultInstance()

                val repoItems = realm.where(RealmUser::class.java)
                    .equalTo("login", user.login)
                    .findFirst()
                    ?.repoItems?.toList()
                    ?.map { items -> items.mapToDomain() }

                if (repoItems != null) {
                    it.onSuccess(repoItems)
                } else {
                    it.onError(Throwable("There is no repos ${user.login} owns in Realm"))
                }
            }
        }
        val single =
            measureTimeInSeconds(block, LOG_MESSAGE_NAMESPACE, "getUserRepos", logcatInfo)
        return single
    }

    override fun saveUser(user: User): Completable {
        val block: () -> Completable = {
            Completable.fromAction {
                val realmUser = user.mapToRealm()

                val realm = Realm.getDefaultInstance()

                realm.executeTransaction {
                    it.copyToRealmOrUpdate(realmUser)
                }
            }
        }
        val completable =
            measureTimeInSeconds(block, LOG_MESSAGE_NAMESPACE, "saveUser", logcatInfo)
        return completable
    }

    override fun deleteUser(user: User): Completable {
        val block: () -> Completable = {
            Completable.create { emitter ->
                val realmUser = user.mapToRealm()

                val realm = Realm.getDefaultInstance()

                realm.executeTransaction {
                    it.where(RealmUser::class.java)
                        .equalTo("id", realmUser.id)
                        .findFirst()
                        ?.deleteFromRealm()
                        ?: emitter.onError(Throwable("There is no user with login ${user.login} to delete"))
                    emitter.onComplete()
                }
            }
        }
        val completable =
            measureTimeInSeconds(block, LOG_MESSAGE_NAMESPACE, "deleteUser", logcatInfo)
        return completable
    }
}
