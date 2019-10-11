package ru.geekbrains.android.level3.valeryvpetrov.di.module

import com.facebook.stetho.okhttp3.StethoInterceptor
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import ru.geekbrains.android.level3.valeryvpetrov.data.remote.github.UserApi
import ru.geekbrains.android.level3.valeryvpetrov.di.QualifierStethoNetworkInterceptor
import javax.inject.Singleton

@Module
class RetrofitModule {

    companion object {

        const val BASE_URL_GITHUB = "https://api.github.com"
    }

    @Provides
    @Singleton
    fun provideGithubUserApi(retrofit: Retrofit): UserApi =
        retrofit.create(UserApi::class.java)

    @Provides
    @Singleton
    fun provideGitHubRetrofit(
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory,
        rxJava2CallAdapterFactory: RxJava2CallAdapterFactory
    ): Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL_GITHUB)
            .client(okHttpClient)
            .addConverterFactory(gsonConverterFactory)
            .addCallAdapterFactory(rxJava2CallAdapterFactory)
            .build()

    @Provides
    @Singleton
    fun provideOkHttpClient(
        @QualifierStethoNetworkInterceptor
        stethoNetworkInterceptor: Interceptor
    ): OkHttpClient =
        OkHttpClient.Builder()
            .addNetworkInterceptor(stethoNetworkInterceptor)
            .build()

    @Provides
    @QualifierStethoNetworkInterceptor
    @Singleton
    fun provideStethoNetworkInterceptor(): Interceptor =
        StethoInterceptor()

    @Provides
    @Singleton
    fun provideGsonConverterFactory(): GsonConverterFactory =
        GsonConverterFactory.create()

    @Provides
    @Singleton
    fun provideRxJava2CallAdapterFactory(): RxJava2CallAdapterFactory =
        RxJava2CallAdapterFactory.create()
}