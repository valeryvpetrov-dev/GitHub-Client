package ru.geekbrains.android.level3.valeryvpetrov.data.network.remote

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import ru.geekbrains.android.level3.valeryvpetrov.data.network.remote.entity.UserItemResponse
import ru.geekbrains.android.level3.valeryvpetrov.data.network.remote.entity.UserResponse

interface UserApi {

    @GET("users")
    fun getUsers(): Call<List<UserItemResponse>>

    @GET("users/{username}")
    fun getUser(@Path("username") username: String): Call<UserResponse>
}