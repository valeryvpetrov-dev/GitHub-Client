package ru.geekbrains.android.level3.valeryvpetrov.presentation.presenter

/**
 * Generic class used to expose UseCase execution result.
 */
sealed class UseCaseResult<T, E>(
    val data: T? = null,
    val error: E? = null
) {
    class Success<T, E>(data: T) : UseCaseResult<T, E>(data)

    class Loading<T, E>(data: T? = null, error: E? = null) : UseCaseResult<T, E>(data, error)

    class Error<T, E>(error: E, data: T? = null) : UseCaseResult<T, E>(data, error)
}