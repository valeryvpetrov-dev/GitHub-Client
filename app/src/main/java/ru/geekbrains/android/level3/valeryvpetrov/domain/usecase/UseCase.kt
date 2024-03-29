package ru.geekbrains.android.level3.valeryvpetrov.domain.usecase

import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import ru.geekbrains.android.level3.valeryvpetrov.domain.executor.IExecutionScheduler
import ru.geekbrains.android.level3.valeryvpetrov.domain.executor.IPostExecutionScheduler

abstract class UseCase<Q, R>(
    protected val executionScheduler: IExecutionScheduler,
    protected val postExecutionScheduler: IPostExecutionScheduler
) {
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    protected abstract fun buildSingle(requestValue: Q): Single<R>

    open fun execute(requestValue: Q, singleObserver: DisposableSingleObserver<R>) {
        val single = buildSingle(requestValue)
            .subscribeOn(executionScheduler.getScheduler())
            .observeOn(postExecutionScheduler.getScheduler())
        compositeDisposable.add(single.subscribeWith(singleObserver))
    }

    fun dispose() {
        if (!compositeDisposable.isDisposed)
            compositeDisposable.dispose()
    }
}