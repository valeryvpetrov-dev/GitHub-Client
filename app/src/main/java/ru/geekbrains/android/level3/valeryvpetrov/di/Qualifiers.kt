package ru.geekbrains.android.level3.valeryvpetrov.di

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class QualifierMainThreadExecutionScheduler

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class QualifierNetworkExecutionScheduler

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class QualifierDiskExecutionScheduler

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class QualifierUserRemoteDataSource

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class QualifierUserLocalDataSource

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class QualifierStethoNetworkInterceptor

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class QualifierRealmGithubDatabaseConfig

