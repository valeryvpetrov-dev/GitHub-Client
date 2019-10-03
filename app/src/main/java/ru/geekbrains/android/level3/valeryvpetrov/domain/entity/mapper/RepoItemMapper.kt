package ru.geekbrains.android.level3.valeryvpetrov.domain.entity.mapper

import ru.geekbrains.android.level3.valeryvpetrov.data.local.realm.entity.RepoItem as RealmRepoItem
import ru.geekbrains.android.level3.valeryvpetrov.data.remote.github.entity.RepoItemResponse as RetrofitRepoItem
import ru.geekbrains.android.level3.valeryvpetrov.domain.entity.RepoItem as DomainRepoItem
import ru.geekbrains.android.level3.valeryvpetrov.presentation.entity.RepoItem as PresentationRepoItem

fun PresentationRepoItem.mapToDomain(): DomainRepoItem {
    return DomainRepoItem(
        this.id,
        this.fullName,
        this.description
    )
}

fun RetrofitRepoItem.mapToDomain(): DomainRepoItem {
    return DomainRepoItem(
        this.id,
        this.fullName,
        this.description
    )
}

fun RealmRepoItem.mapToDomain(): DomainRepoItem {
    return DomainRepoItem(
        this.id,
        this.fullName,
        this.description
    )
}