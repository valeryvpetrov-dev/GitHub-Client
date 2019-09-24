package ru.geekbrains.android.level3.valeryvpetrov.domain.usecase

import androidx.annotation.MainThread
import androidx.annotation.WorkerThread

interface UseCaseScheduler {

    @MainThread
    fun execute(runnable: Runnable)

    @WorkerThread
    fun <R : UseCase.ResponseValue, E : UseCase.Error> notifySuccess(
        responseValue: R,
        callback: UseCase.Callback<R, E>
    )

    @WorkerThread
    fun <R : UseCase.ResponseValue, E : UseCase.Error> notifyError(
        error: E,
        callback: UseCase.Callback<R, E>
    )
}