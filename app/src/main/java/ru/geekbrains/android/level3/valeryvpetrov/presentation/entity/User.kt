package ru.geekbrains.android.level3.valeryvpetrov.presentation.entity

data class User(
    val login: String,
    val name: String?,
    val company: String?,
    val blog: String?,
    val location: String?,
    val email: String?,
    val bio: String?
)