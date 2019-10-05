package ru.geekbrains.android.level3.valeryvpetrov.data.local.room.entity.mapper

import ru.geekbrains.android.level3.valeryvpetrov.data.local.room.entity.RepoItem
import ru.geekbrains.android.level3.valeryvpetrov.domain.entity.RepoItem as DomainRepoItem

fun DomainRepoItem.mapToRoom(userId: Int): RepoItem {
    return RepoItem(
        this.id,
        this.fullName,
        this.description,
        userId
    )
}