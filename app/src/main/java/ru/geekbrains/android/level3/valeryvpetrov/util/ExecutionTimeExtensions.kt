package ru.geekbrains.android.level3.valeryvpetrov.util

inline fun <T> measureTimeMillis(
    block: () -> T,
    loggingFunction: (time: Double) -> Unit
): T {
    val start = System.currentTimeMillis()
    val result = block()
    loggingFunction.invoke((System.currentTimeMillis() - start) / 1000.0)
    return result
}