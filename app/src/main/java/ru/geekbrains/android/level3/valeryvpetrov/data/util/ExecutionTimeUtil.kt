package ru.geekbrains.android.level3.valeryvpetrov.data.util

import android.util.Log

const val LOG_TAG_EXECUTION_TIME = "ExecutionTime"

const val LOG_MESSAGE_TEMPLATE_EXECUTION_TIME = "%-10s :: %-20s :: %.5f secs"

val logcatInfo: (String, String, Double) -> Unit = { namespace, operation, time ->
    Log.i(
        LOG_TAG_EXECUTION_TIME,
        LOG_MESSAGE_TEMPLATE_EXECUTION_TIME.format(namespace, operation, time)
    )
}

inline fun <T> measureTimeInSeconds(
    block: () -> T,
    namespace: String,
    operationName: String,
    loggingFunction: (namespace: String, operationName: String, time: Double) -> Unit
): T {
    val start = System.currentTimeMillis()
    val result = block()
    loggingFunction.invoke(namespace, operationName, (System.currentTimeMillis() - start) / 1000.0)
    return result
}