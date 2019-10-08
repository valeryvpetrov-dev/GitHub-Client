package ru.geekbrains.android.level3.valeryvpetrov.di.module

import dagger.Module
import dagger.Provides
import io.realm.RealmConfiguration
import ru.geekbrains.android.level3.valeryvpetrov.di.QualifierRealmGithubDatabaseConfig
import javax.inject.Singleton

@Module
class RealmModule {

    companion object {

        const val REALM_GITHUB_DATABASE = "github.realm"
    }

    @Provides
    @QualifierRealmGithubDatabaseConfig
    @Singleton
    fun provideRealmGithubDatabaseConfig(): RealmConfiguration =
        RealmConfiguration.Builder()
            .name(REALM_GITHUB_DATABASE)
            .build()
}