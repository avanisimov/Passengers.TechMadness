package com.passengers.anroidapp.core

import android.os.Bundle
import androidx.fragment.app.Fragment
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import toothpick.Toothpick
import toothpick.config.Module
import java.util.*

abstract class BaseFragment : Fragment() {


    protected var fragmentScopeKey: String? = null


    private val compositeDisposable = CompositeDisposable()

    protected fun addDisposables(vararg disposables: Disposable) {
        for (d in disposables) {
            compositeDisposable.add(d)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        fragmentScopeKey = if (savedInstanceState != null) {
            savedInstanceState.getString(RosbankApplication.SCOPE_KEY)
        } else {
            this.toString()
        }

        super.onCreate(savedInstanceState)

        val baseActivity = activity as BaseActivity?
        val scope = Toothpick.openScopes(
                RosbankApplication.APP_SCOPE_KEY,
                baseActivity!!.activityScopeKey,
                fragmentScopeKey
        )
        scope.installModules(onCreateModule())

        Toothpick.inject(this, scope)
    }

    override fun onPause() {
        compositeDisposable.clear()
        super.onPause()
    }

    open fun onCreateModule(): Module {
        return Module()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (isRemoving) {
            compositeDisposable.clear()
            Toothpick.closeScope(fragmentScopeKey)
        }
    }
}