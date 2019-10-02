package ru.geekbrains.android.level3.valeryvpetrov.presentation.executor

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import ru.geekbrains.android.level3.valeryvpetrov.domain.executor.IPostExecutionScheduler

class MainThreadExecutionScheduler : IPostExecutionScheduler {

    override fun getScheduler(): Scheduler {
        return AndroidSchedulers.mainThread()
    }
}