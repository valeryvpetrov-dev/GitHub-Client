package ru.geekbrains.android.level3.valeryvpetrov.domain.entity

data class User(
    val login: String,
    val id: Int,
    val name: String,
    val company: String,
    val blog: String,
    val location: String,
    val email: String,
    val bio: String
)