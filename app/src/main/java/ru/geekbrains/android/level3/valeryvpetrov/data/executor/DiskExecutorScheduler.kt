package ru.geekbrains.android.level3.valeryvpetrov.data.executor

import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers
import ru.geekbrains.android.level3.valeryvpetrov.domain.executor.IExecutionScheduler

class DiskExecutorScheduler : IExecutionScheduler {

    override fun getScheduler(): Scheduler {
        return Schedulers.io()
    }
}