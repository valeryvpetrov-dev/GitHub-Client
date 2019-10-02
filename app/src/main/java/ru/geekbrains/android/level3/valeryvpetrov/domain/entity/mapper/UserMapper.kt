package ru.geekbrains.android.level3.valeryvpetrov.domain.entity.mapper

import ru.geekbrains.android.level3.valeryvpetrov.data.remote.entity.UserResponse
import ru.geekbrains.android.level3.valeryvpetrov.domain.entity.User as DomainUser

fun UserResponse.mapToDomain(): DomainUser {
    return DomainUser(
        this.id,
        this.login,
        this.name,
        this.company,
        this.blog,
        this.location,
        this.email,
        this.bio
    )
}