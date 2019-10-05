package ru.geekbrains.android.level3.valeryvpetrov.data.local.room.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [ForeignKey(
        entity = User::class,
        parentColumns = ["id"],
        childColumns = ["userId"],
        onDelete = CASCADE
    )]
)
data class RepoItem(
    @PrimaryKey val id: Int,
    val fullName: String,
    val description: String?,
    val userId: Int
)