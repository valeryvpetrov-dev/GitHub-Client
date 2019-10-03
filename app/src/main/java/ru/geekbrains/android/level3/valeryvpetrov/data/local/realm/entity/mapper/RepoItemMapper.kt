package ru.geekbrains.android.level3.valeryvpetrov.data.local.realm.entity.mapper

import ru.geekbrains.android.level3.valeryvpetrov.data.local.realm.entity.RepoItem
import ru.geekbrains.android.level3.valeryvpetrov.domain.entity.RepoItem as DomainRepoItem

fun DomainRepoItem.mapToRealm(): RepoItem {
    return RepoItem(
        this.id,
        this.fullName,
        this.description
    )
}