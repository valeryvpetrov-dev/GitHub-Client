package ru.geekbrains.android.level3.valeryvpetrov.domain.executor

import io.reactivex.Scheduler

interface IExecutionScheduler {

    fun getScheduler(): Scheduler
}