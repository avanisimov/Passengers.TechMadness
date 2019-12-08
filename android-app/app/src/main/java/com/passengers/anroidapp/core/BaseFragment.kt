package com.passengers.anroidapp.core

import androidx.fragment.app.Fragment
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import ru.terrakok.cicerone.Router

abstract class BaseFragment : Fragment() {


    protected var router: Router? = RosbankApplication.INSTANCE.getRouter()
    private val compositeDisposable = CompositeDisposable()

    protected fun addDisposables(vararg disposables: Disposable) {
        for (d in disposables) {
            compositeDisposable.add(d)
        }
    }

    override fun onPause() {
        compositeDisposable.clear()
        super.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (isRemoving) {
            compositeDisposable.clear()
        }
    }
}