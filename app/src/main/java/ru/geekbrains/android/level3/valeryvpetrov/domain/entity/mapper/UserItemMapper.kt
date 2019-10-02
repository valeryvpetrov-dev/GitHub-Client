package ru.geekbrains.android.level3.valeryvpetrov.domain.entity.mapper

import ru.geekbrains.android.level3.valeryvpetrov.data.remote.entity.UserItemResponse
import ru.geekbrains.android.level3.valeryvpetrov.domain.entity.UserItem as DomainUserItem

fun UserItemResponse.mapToDomain(): DomainUserItem {
    return DomainUserItem(
        this.login,
        this.id
    )
}