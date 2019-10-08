package ru.geekbrains.android.level3.valeryvpetrov.di.module

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import ru.geekbrains.android.level3.valeryvpetrov.data.local.room.dao.RepoDao
import ru.geekbrains.android.level3.valeryvpetrov.data.local.room.dao.UserDao
import ru.geekbrains.android.level3.valeryvpetrov.data.local.room.db.GitHubDatabase
import javax.inject.Singleton

@Module
class RoomModule {

    companion object {

        const val ROOM_GITHUB_DATABASE = "github.db"
    }

    @Provides
    @Singleton
    fun provideUserDao(roomGitHubDatabase: GitHubDatabase): UserDao =
        roomGitHubDatabase.userDao()

    @Provides
    @Singleton
    fun provideRepoDao(roomGitHubDatabase: GitHubDatabase): RepoDao =
        roomGitHubDatabase.repoDao()

    @Provides
    @Singleton
    fun provideRoomGitHubDatabase(context: Context): GitHubDatabase =
        Room.databaseBuilder(
            context,
            GitHubDatabase::class.java,
            ROOM_GITHUB_DATABASE
        ).build()
}