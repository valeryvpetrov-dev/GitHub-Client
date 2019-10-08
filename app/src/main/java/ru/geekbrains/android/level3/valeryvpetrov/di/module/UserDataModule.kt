package ru.geekbrains.android.level3.valeryvpetrov.di.module

import dagger.Module
import dagger.Provides
import ru.geekbrains.android.level3.valeryvpetrov.data.local.datasource.UserLocalDataSource
import ru.geekbrains.android.level3.valeryvpetrov.data.local.realm.datasource.UserRealmDataSource
import ru.geekbrains.android.level3.valeryvpetrov.data.local.room.dao.RepoDao
import ru.geekbrains.android.level3.valeryvpetrov.data.local.room.dao.UserDao
import ru.geekbrains.android.level3.valeryvpetrov.data.local.room.datasource.UserRoomDataSource
import ru.geekbrains.android.level3.valeryvpetrov.data.remote.datasource.UserRemoteDataSource
import ru.geekbrains.android.level3.valeryvpetrov.data.remote.github.UserApi
import ru.geekbrains.android.level3.valeryvpetrov.data.repository.UserRepository
import ru.geekbrains.android.level3.valeryvpetrov.data.repository.datasource.IUserDataSource
import ru.geekbrains.android.level3.valeryvpetrov.di.QualifierUserLocalDataSource
import ru.geekbrains.android.level3.valeryvpetrov.di.QualifierUserRemoteDataSource
import ru.geekbrains.android.level3.valeryvpetrov.domain.repository.IUserRepository
import javax.inject.Singleton

@Module
class UserDataModule {

    @Provides
    @Singleton
    fun provideUserRepository(
        @QualifierUserRemoteDataSource
        userRemoteDataSource: IUserDataSource,
        @QualifierUserLocalDataSource
        userLocalDataSource: IUserDataSource
    ): IUserRepository =
        UserRepository(userRemoteDataSource, userLocalDataSource)

    @Provides
    @QualifierUserRemoteDataSource
    @Singleton
    fun provideUserRemoteDataSource(userApi: UserApi): IUserDataSource =
        UserRemoteDataSource(userApi)

    @Provides
    @QualifierUserLocalDataSource
    @Singleton
    fun provideUserLocalDataSource(
        userRealmDataSource: UserRealmDataSource,
        userRoomDataSource: UserRoomDataSource
    ): IUserDataSource =
        UserLocalDataSource(userRealmDataSource, userRoomDataSource)

    @Provides
    @Singleton
    fun provideUserRealmDataSource(): UserRealmDataSource =
        UserRealmDataSource()

    @Provides
    @Singleton
    fun provideUserRoomDataSource(userDao: UserDao, repoDao: RepoDao): UserRoomDataSource =
        UserRoomDataSource(userDao, repoDao)
}