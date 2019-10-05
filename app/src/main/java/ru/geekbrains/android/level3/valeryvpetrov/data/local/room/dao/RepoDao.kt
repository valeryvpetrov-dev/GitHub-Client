package ru.geekbrains.android.level3.valeryvpetrov.data.local.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import io.reactivex.Completable
import io.reactivex.Single
import ru.geekbrains.android.level3.valeryvpetrov.data.local.room.entity.RepoItem

@Dao
interface RepoDao {

    @Query("SELECT * FROM RepoItem WHERE userId = :userId")
    fun selectByUserId(userId: Int): Single<List<RepoItem>>

    @Insert
    fun insert(repoItems: List<RepoItem>): Completable

    @Delete
    fun delete(repoItems: List<RepoItem>): Completable
}