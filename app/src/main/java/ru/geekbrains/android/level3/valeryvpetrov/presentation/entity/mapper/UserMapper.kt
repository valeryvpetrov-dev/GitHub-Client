package ru.geekbrains.android.level3.valeryvpetrov.presentation.entity.mapper

import ru.geekbrains.android.level3.valeryvpetrov.presentation.entity.User
import ru.geekbrains.android.level3.valeryvpetrov.domain.entity.User as DomainUser

fun DomainUser.mapToPresentation(): User {
    return User(
        this.login,
        this.name,
        this.company,
        this.blog,
        this.location,
        this.email,
        this.bio,
        this.repoItems?.map { it.mapToPresentation() }
    )
}