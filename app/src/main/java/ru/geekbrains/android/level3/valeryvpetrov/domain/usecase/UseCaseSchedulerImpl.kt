package ru.geekbrains.android.level3.valeryvpetrov.domain.usecase

import ru.geekbrains.android.level3.valeryvpetrov.util.AppExecutors

class UseCaseSchedulerImpl(
    private val appExecutors: AppExecutors
) : UseCaseScheduler {

    override fun execute(runnable: Runnable) {
        appExecutors.useCaseThread.execute(runnable)
    }

    override fun <R : UseCase.ResponseValue, E : UseCase.Error> notifySuccess(
        responseValue: R,
        callback: UseCase.Callback<R, E>
    ) {
        appExecutors.mainThread.execute {
            callback.onSuccess(responseValue)
        }
    }

    override fun <R : UseCase.ResponseValue, E : UseCase.Error> notifyError(
        error: E,
        callback: UseCase.Callback<R, E>
    ) {
        appExecutors.mainThread.execute {
            callback.onError(error)
        }
    }
}