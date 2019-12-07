package com.passengers.anroidapp.core

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import ru.terrakok.cicerone.Router

abstract class BaseViewModel : ViewModel(){

    protected var router: Router? = RosbankApplication.INSTANCE.getRouter()
    private val compositeDisposable = CompositeDisposable()


    protected fun addDisposables(vararg disposables: Disposable?) {
        for (d in disposables) {
            compositeDisposable.add(d!!)
        }
    }

    abstract fun init()

    public override fun onCleared() {
        compositeDisposable.clear()
    }

}