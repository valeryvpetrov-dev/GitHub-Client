package ru.geekbrains.android.level3.valeryvpetrov

import com.facebook.stetho.Stetho
import io.realm.Realm
import ru.geekbrains.android.level3.valeryvpetrov.di.component.*
import ru.geekbrains.android.level3.valeryvpetrov.di.module.*
import android.app.Application as AndroidApplication

class Application : AndroidApplication() {

    lateinit var applicationComponent: ApplicationComponent
    var userComponent: UserComponent? = null
    var userRepoItemsScreenComponent: UserRepoItemsScreenComponent? = null

    override fun onCreate() {
        super.onCreate()

        Stetho.initializeWithDefaults(this)

        applicationComponent = DaggerApplicationComponent.builder()
            .applicationModule(ApplicationModule(this))
            .executorsModule(ExecutorsModule())
            .retrofitModule(RetrofitModule())
            .realmModule(RealmModule())
            .roomModule(RoomModule())
            .userDataModule(UserDataModule())
            .build()

        Realm.init(applicationComponent.context())
        Realm.setDefaultConfiguration(applicationComponent.realmGithubDatabaseConfig())

        addUserComponent()
    }

    fun addUserComponent(): UserComponent? {
        if (userComponent == null) {
            userComponent = DaggerUserComponent.builder()
                .applicationComponent(applicationComponent)
                .userUseCaseModule(UserUseCaseModule())
                .build()
        }
        return userComponent
    }

    fun removeUserComponent() {
        userComponent = null
    }

    fun addUserRepoItemsScreenComponent(): UserRepoItemsScreenComponent? {
        if (userRepoItemsScreenComponent == null) {
            userRepoItemsScreenComponent = DaggerUserRepoItemsScreenComponent.builder()
                .userComponent(userComponent)
                .viewModelModule(ViewModelModule())
                .build()
        }
        return userRepoItemsScreenComponent
    }

    fun removeUserRepoItemsScreenComponent() {
        userRepoItemsScreenComponent = null
    }

}