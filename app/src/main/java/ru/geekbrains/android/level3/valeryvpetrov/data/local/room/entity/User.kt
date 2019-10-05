package ru.geekbrains.android.level3.valeryvpetrov.data.local.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @PrimaryKey var id: Int,
    var login: String,
    var name: String?,
    var company: String?,
    var blog: String?,
    var location: String?,
    var email: String?,
    var bio: String?
) {
    constructor() : this(0, null.toString(), null, null, null, null, null, null)
}