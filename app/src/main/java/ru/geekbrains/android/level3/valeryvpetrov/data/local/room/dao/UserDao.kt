package ru.geekbrains.android.level3.valeryvpetrov.data.local.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import io.reactivex.Completable
import io.reactivex.Single
import ru.geekbrains.android.level3.valeryvpetrov.data.local.room.entity.User

@Dao
interface UserDao {

    @Query("SELECT * FROM User WHERE login = :login")
    fun selectByLogin(login: String): Single<User>

    @Insert
    fun insert(user: User): Completable

    @Delete
    fun delete(user: User): Completable
}