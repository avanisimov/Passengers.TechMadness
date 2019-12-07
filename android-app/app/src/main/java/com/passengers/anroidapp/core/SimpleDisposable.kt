package com.passengers.anroidapp.core

import io.reactivex.observers.DisposableObserver

open class SimpleDisposable<T> : DisposableObserver<T>() {
    override fun onNext(t: T) {}
    override fun onError(e: Throwable) {}
    override fun onComplete() {}
}