package ru.geekbrains.android.level3.valeryvpetrov.data.local.room.db

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.geekbrains.android.level3.valeryvpetrov.data.local.room.dao.RepoDao
import ru.geekbrains.android.level3.valeryvpetrov.data.local.room.dao.UserDao
import ru.geekbrains.android.level3.valeryvpetrov.data.local.room.entity.RepoItem
import ru.geekbrains.android.level3.valeryvpetrov.data.local.room.entity.User

@Database(entities = [User::class, RepoItem::class], version = 1)
abstract class GitHubDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

    abstract fun repoDao(): RepoDao
}