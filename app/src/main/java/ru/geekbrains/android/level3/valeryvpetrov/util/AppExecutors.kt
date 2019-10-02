package ru.geekbrains.android.level3.valeryvpetrov.util

import ru.geekbrains.android.level3.valeryvpetrov.data.executor.NetworkExecutionScheduler
import ru.geekbrains.android.level3.valeryvpetrov.domain.executor.IExecutionScheduler
import ru.geekbrains.android.level3.valeryvpetrov.domain.executor.IPostExecutionScheduler
import ru.geekbrains.android.level3.valeryvpetrov.presentation.executor.MainThreadExecutionScheduler

class AppExecutors(
    val networkIo: IExecutionScheduler = NetworkExecutionScheduler(),
    val mainThread: IPostExecutionScheduler = MainThreadExecutionScheduler()
)