package com.passengers.anroidapp.core

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import ru.terrakok.cicerone.Router

abstract class BaseViewModel {

    protected var router: Router?
    private val compositeDisposable = CompositeDisposable()


    protected fun addDisposables(vararg disposables: Disposable?) {
        for (d in disposables) {
            compositeDisposable.add(d!!)
        }
    }

    abstract fun init()

    fun onCleared() {
        compositeDisposable.clear()
    }

    init {
        router = RosbankApplication.INSTANCE.getRouter()
    }
}