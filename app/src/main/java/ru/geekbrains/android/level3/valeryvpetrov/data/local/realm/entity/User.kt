package ru.geekbrains.android.level3.valeryvpetrov.data.local.realm.entity

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class User(
    @PrimaryKey open var id: Int = 0,
    open var login: String = "",
    open var name: String? = null,
    open var company: String? = null,
    open var blog: String? = null,
    open var location: String? = null,
    open var email: String? = null,
    open var bio: String? = null,
    open var repoItems: RealmList<RepoItem>? = null
) : RealmObject()