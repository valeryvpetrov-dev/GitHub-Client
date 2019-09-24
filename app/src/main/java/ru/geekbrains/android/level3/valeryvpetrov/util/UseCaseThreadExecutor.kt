package ru.geekbrains.android.level3.valeryvpetrov.util

import java.util.concurrent.ArrayBlockingQueue
import java.util.concurrent.Executor
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit

class UseCaseThreadExecutor(
    private val executor: Executor = ThreadPoolExecutor(
        POOL_SIZE,
        MAX_POOL_SIZE,
        TIMEOUT.toLong(),
        TimeUnit.SECONDS,
        ArrayBlockingQueue<Runnable>(POOL_SIZE)
    )
) : Executor {

    companion object {

        const val POOL_SIZE = 2
        const val MAX_POOL_SIZE = 4
        const val TIMEOUT = 30
    }

    override fun execute(command: Runnable) {
        executor.execute(command)
    }
}