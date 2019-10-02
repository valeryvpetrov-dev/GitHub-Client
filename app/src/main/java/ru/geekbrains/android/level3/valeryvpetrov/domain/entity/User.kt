package ru.geekbrains.android.level3.valeryvpetrov.domain.entity

data class User(
    val id: Int,
    val login: String,
    val name: String?,
    val company: String?,
    val blog: String?,
    val location: String?,
    val email: String?,
    val bio: String?,
    var repoItems: List<RepoItem>?
)