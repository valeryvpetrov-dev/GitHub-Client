package ru.geekbrains.android.level3.valeryvpetrov.data.remote.github

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import ru.geekbrains.android.level3.valeryvpetrov.data.remote.github.entity.RepoItemResponse
import ru.geekbrains.android.level3.valeryvpetrov.data.remote.github.entity.UserItemResponse
import ru.geekbrains.android.level3.valeryvpetrov.data.remote.github.entity.UserResponse

interface UserApi {

    @GET("users")
    fun getUsers(): Single<List<UserItemResponse>>

    @GET("users/{username}")
    fun getUser(@Path("username") username: String): Single<UserResponse>

    @GET("users/{username}/repos")
    fun getUserRepos(@Path("username") username: String): Single<List<RepoItemResponse>>
}