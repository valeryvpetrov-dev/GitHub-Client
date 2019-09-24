package ru.geekbrains.android.level3.valeryvpetrov.util

import android.os.Handler
import android.os.Looper
import java.util.concurrent.Executor

class MainThreadExecutor(
    private val handler: Handler = Handler(Looper.getMainLooper())
) : Executor {

    override fun execute(command: Runnable) {
        handler.post(command)
    }
}