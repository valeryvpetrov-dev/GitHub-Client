package ru.geekbrains.android.level3.valeryvpetrov.presentation.entity.mapper

import ru.geekbrains.android.level3.valeryvpetrov.presentation.entity.RepoItem
import ru.geekbrains.android.level3.valeryvpetrov.domain.entity.RepoItem as DomainRepoItem

fun DomainRepoItem.mapToPresentation(): RepoItem {
    return RepoItem(
        this.id,
        this.fullName,
        this.description
    )
}