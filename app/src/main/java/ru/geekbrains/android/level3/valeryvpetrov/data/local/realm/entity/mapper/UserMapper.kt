package ru.geekbrains.android.level3.valeryvpetrov.data.local.realm.entity.mapper

import io.realm.RealmList
import ru.geekbrains.android.level3.valeryvpetrov.data.local.realm.entity.RepoItem
import ru.geekbrains.android.level3.valeryvpetrov.data.local.realm.entity.User
import ru.geekbrains.android.level3.valeryvpetrov.domain.entity.User as DomainUser

fun DomainUser.mapToRealm(): User {
    val repoItemsRealm = RealmList<RepoItem>()
    this.repoItems?.toList()
        ?.map { it.mapToRealm() }
        ?.forEach { repoItemsRealm.add(it) }

    return User(
        this.id,
        this.login,
        this.name,
        this.company,
        this.blog,
        this.location,
        this.email,
        this.bio,
        repoItemsRealm
    )
}