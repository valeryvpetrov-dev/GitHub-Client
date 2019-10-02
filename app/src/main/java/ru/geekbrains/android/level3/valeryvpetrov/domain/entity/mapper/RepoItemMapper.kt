package ru.geekbrains.android.level3.valeryvpetrov.domain.entity.mapper

import ru.geekbrains.android.level3.valeryvpetrov.data.remote.entity.RepoItemResponse
import ru.geekbrains.android.level3.valeryvpetrov.domain.entity.RepoItem as DomainRepoItem

fun RepoItemResponse.mapToDomain(): DomainRepoItem {
    return DomainRepoItem(
        this.id,
        this.fullName,
        this.description
    )
}