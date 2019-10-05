package ru.geekbrains.android.level3.valeryvpetrov.data.local.room.entity.mapper

import ru.geekbrains.android.level3.valeryvpetrov.data.local.room.entity.RepoItem
import ru.geekbrains.android.level3.valeryvpetrov.data.local.room.entity.User
import ru.geekbrains.android.level3.valeryvpetrov.domain.entity.User as DomainUser

fun DomainUser.mapToRoom(): Pair<User, List<RepoItem>?> {
    val user = User(
        this.id,
        this.login,
        this.name,
        this.company,
        this.blog,
        this.location,
        this.email,
        this.bio
    )
    val repoItems = this.repoItems?.map { it.mapToRoom(user.id) }
    return user to repoItems
}