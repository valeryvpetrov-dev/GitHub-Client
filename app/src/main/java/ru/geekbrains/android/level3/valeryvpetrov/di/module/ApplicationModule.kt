package ru.geekbrains.android.level3.valeryvpetrov.di.module

import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ApplicationModule(
    private val context: Context
) {

    @Provides
    @Singleton
    fun provideContext(): Context = context
}