package ru.geekbrains.android.level3.valeryvpetrov.util

import java.util.concurrent.Executor
import java.util.concurrent.Executors

class NetworkExecutor(
    private val executor: Executor = Executors.newFixedThreadPool(THREAD_COUNT)
) : Executor {

    companion object {

        const val THREAD_COUNT = 3
    }

    override fun execute(command: Runnable) {
        executor.execute(command)
    }
}