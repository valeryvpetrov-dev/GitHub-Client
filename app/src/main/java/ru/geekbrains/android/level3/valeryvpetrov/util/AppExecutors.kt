package ru.geekbrains.android.level3.valeryvpetrov.util

import java.util.concurrent.Executor

class AppExecutors(
    val useCaseThread: Executor = UseCaseThreadExecutor(),
    val networkIo: Executor = NetworkExecutor(),
    val mainThread: Executor = MainThreadExecutor()
)