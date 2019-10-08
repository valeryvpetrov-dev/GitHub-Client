package ru.geekbrains.android.level3.valeryvpetrov.di.component

import android.content.Context
import dagger.Component
import io.realm.RealmConfiguration
import ru.geekbrains.android.level3.valeryvpetrov.di.QualifierDiskExecutionScheduler
import ru.geekbrains.android.level3.valeryvpetrov.di.QualifierMainThreadExecutionScheduler
import ru.geekbrains.android.level3.valeryvpetrov.di.QualifierNetworkExecutionScheduler
import ru.geekbrains.android.level3.valeryvpetrov.di.QualifierRealmGithubDatabaseConfig
import ru.geekbrains.android.level3.valeryvpetrov.di.module.*
import ru.geekbrains.android.level3.valeryvpetrov.domain.executor.IExecutionScheduler
import ru.geekbrains.android.level3.valeryvpetrov.domain.executor.IPostExecutionScheduler
import ru.geekbrains.android.level3.valeryvpetrov.domain.repository.IUserRepository
import ru.geekbrains.android.level3.valeryvpetrov.util.ConnectivityManager
import javax.inject.Singleton

@Component(
    modules = [
        ApplicationModule::class,
        ExecutorsModule::class,
        UtilsModule::class,
        RetrofitModule::class,
        RealmModule::class,
        RoomModule::class,
        UserDataModule::class
    ]
)
@Singleton
interface ApplicationComponent {

    fun context(): Context

    fun connectivityManager(): ConnectivityManager

    @QualifierNetworkExecutionScheduler
    fun networkExecutionScheduler(): IExecutionScheduler

    @QualifierDiskExecutionScheduler
    fun diskExecutionScheduler(): IExecutionScheduler

    @QualifierMainThreadExecutionScheduler
    fun mainThreadExecutionScheduler(): IPostExecutionScheduler

    fun userRepository(): IUserRepository

    @QualifierRealmGithubDatabaseConfig
    fun realmGithubDatabaseConfig(): RealmConfiguration
}