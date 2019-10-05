package ru.geekbrains.android.level3.valeryvpetrov.domain.entity.mapper

import ru.geekbrains.android.level3.valeryvpetrov.data.local.realm.entity.User as RealmUser
import ru.geekbrains.android.level3.valeryvpetrov.data.local.room.entity.User as RoomUser
import ru.geekbrains.android.level3.valeryvpetrov.data.remote.github.entity.UserResponse as RetrofitUser
import ru.geekbrains.android.level3.valeryvpetrov.domain.entity.User as DomainUser
import ru.geekbrains.android.level3.valeryvpetrov.presentation.entity.User as PresentationUser

fun PresentationUser.mapToDomain(): DomainUser {
    return DomainUser(
        this.id,
        this.login,
        this.name,
        this.company,
        this.blog,
        this.location,
        this.email,
        this.bio,
        this.repoItems?.toList()?.map { it.mapToDomain() }
    )
}

fun RetrofitUser.mapToDomain(): DomainUser {
    return DomainUser(
        this.id,
        this.login,
        this.name,
        this.company,
        this.blog,
        this.location,
        this.email,
        this.bio,
        null
    )
}

fun RealmUser.mapToDomain(): DomainUser {
    return DomainUser(
        this.id,
        this.login,
        this.name,
        this.company,
        this.blog,
        this.location,
        this.email,
        this.bio,
        this.repoItems?.toList()?.map { it.mapToDomain() }
    )
}

fun RoomUser.mapToDomain(): DomainUser {
    return DomainUser(
        this.id,
        this.login,
        this.name,
        this.company,
        this.blog,
        this.location,
        this.email,
        this.bio,
        null
    )
}