package ru.geekbrains.android.level3.valeryvpetrov.domain.entity.mapper

import ru.geekbrains.android.level3.valeryvpetrov.data.remote.github.entity.UserItemResponse as RetrofitUserItemResponse
import ru.geekbrains.android.level3.valeryvpetrov.domain.entity.UserItem as DomainUserItem

fun RetrofitUserItemResponse.mapToDomain(): DomainUserItem {
    return DomainUserItem(
        this.login,
        this.id
    )
}