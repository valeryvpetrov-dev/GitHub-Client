package ru.geekbrains.android.level3.valeryvpetrov

import io.realm.Realm
import io.realm.RealmConfiguration
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import ru.geekbrains.android.level3.valeryvpetrov.util.AppExecutors
import android.app.Application as AndroidApplication

class Application : AndroidApplication() {

    companion object {

        const val BASE_URL_GITHUB = "https://api.github.com"

        const val REALM_GITHUB_DATABASE = "github.realm"
    }

    val retrofitGithub: Retrofit by lazy {
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()

        Retrofit.Builder()
            .baseUrl(BASE_URL_GITHUB)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

    private val realmDefaultConfig: RealmConfiguration by lazy {
        RealmConfiguration.Builder()
            .name(REALM_GITHUB_DATABASE)
            .build()
    }

    val appExecutors: AppExecutors by lazy {
        AppExecutors()
    }

    override fun onCreate() {
        super.onCreate()

        Realm.init(this)
        Realm.setDefaultConfiguration(realmDefaultConfig)
    }
}