package com.passengers.anroidapp.core

import androidx.fragment.app.Fragment
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BaseFragment : Fragment() {

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