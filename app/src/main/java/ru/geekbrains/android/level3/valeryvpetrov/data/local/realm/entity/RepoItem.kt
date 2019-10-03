package ru.geekbrains.android.level3.valeryvpetrov.data.local.realm.entity

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class RepoItem(
    @PrimaryKey open var id: Int = 0,
    open var fullName: String = "",
    open var description: String? = null
) : RealmObject()