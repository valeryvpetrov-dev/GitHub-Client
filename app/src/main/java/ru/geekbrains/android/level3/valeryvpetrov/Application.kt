package ru.geekbrains.android.level3.valeryvpetrov

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.geekbrains.android.level3.valeryvpetrov.util.AppExecutors
import android.app.Application as AndroidApplication

class Application : AndroidApplication() {

    companion object {
        const val BASE_URL_GITHUB = "https://api.github.com"
    }

    val retrofitGithub: Retrofit by lazy {
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()

        Retrofit.Builder()
            .baseUrl(BASE_URL_GITHUB)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val appExecutors: AppExecutors by lazy {
        AppExecutors()
    }
}