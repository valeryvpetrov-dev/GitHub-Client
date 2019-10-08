package ru.geekbrains.android.level3.valeryvpetrov.di.module

import android.content.Context
import dagger.Module
import dagger.Provides
import ru.geekbrains.android.level3.valeryvpetrov.util.ConnectivityManager
import javax.inject.Singleton

@Module
class UtilsModule() {

    @Provides
    @Singleton
    fun provideConnectivityManager(context: Context): ConnectivityManager =
        ConnectivityManager(context)
}