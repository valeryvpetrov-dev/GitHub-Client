package ru.geekbrains.android.level3.valeryvpetrov.domain.usecase

import androidx.annotation.MainThread
import androidx.annotation.WorkerThread

abstract class UseCase<Q : UseCase.RequestValue, R : UseCase.ResponseValue, E : UseCase.Error>() {

    lateinit var requestValue: Q

    lateinit var callback: Callback<R, E>

    interface RequestValue {}

    interface ResponseValue {}

    interface Error {}

    interface Callback<R, E> {

        @MainThread
        fun onSuccess(response: R)

        @MainThread
        fun onError(error: E)
    }

    @WorkerThread
    abstract fun execute(requestValue: Q)
}