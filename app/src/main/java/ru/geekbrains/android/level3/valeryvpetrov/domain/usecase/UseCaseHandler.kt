package ru.geekbrains.android.level3.valeryvpetrov.domain.usecase

import androidx.annotation.MainThread
import androidx.annotation.WorkerThread
import ru.geekbrains.android.level3.valeryvpetrov.util.AppExecutors

class UseCaseHandler(
    private val useCaseScheduler: UseCaseScheduler
) {

    companion object {

        private var instance: UseCaseHandler? = null

        fun getInstance(appExecutors: AppExecutors) =
            instance ?: UseCaseHandler(UseCaseSchedulerImpl(appExecutors)).apply { instance = this }
    }

    @MainThread
    fun <Q : UseCase.RequestValue, R : UseCase.ResponseValue, E : UseCase.Error> execute(
        useCase: UseCase<Q, R, E>,
        requestValue: Q,
        callback: UseCase.Callback<R, E>
    ) {
        useCase.requestValue = requestValue
        useCase.callback = UiCallbackWrapper(callback, useCaseScheduler)

        useCaseScheduler.execute(Runnable {
            useCase.execute(requestValue)
        })
    }

    class UiCallbackWrapper<R : UseCase.ResponseValue, E : UseCase.Error>(
        private val callback: UseCase.Callback<R, E>,
        private val useCaseScheduler: UseCaseScheduler
    ) : UseCase.Callback<R, E> {

        @WorkerThread
        override fun onSuccess(response: R) {
            useCaseScheduler.notifySuccess(response, callback)
        }

        @WorkerThread
        override fun onError(error: E) {
            useCaseScheduler.notifyError(error, callback)
        }
    }
}