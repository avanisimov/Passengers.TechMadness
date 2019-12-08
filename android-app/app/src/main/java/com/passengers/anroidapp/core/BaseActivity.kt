package com.passengers.anroidapp.core

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.passengers.anroidapp.R
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import ru.terrakok.cicerone.Router
import ru.terrakok.cicerone.android.support.SupportAppNavigator

abstract class BaseActivity : AppCompatActivity() {

    protected var router: Router? = null

    private val compositeDisposable = CompositeDisposable()

    protected fun addDisposables(vararg disposables: Disposable) {
        for (d in disposables) {
            compositeDisposable.add(d)
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        router = RosbankApplication.INSTANCE.getRouter()

    }

    override fun onResume() {
        super.onResume()
        RosbankApplication.INSTANCE.getNavigatorHolder()!!.setNavigator(SupportAppNavigator(this, R.id.container))
    }

    override fun onPause() {
        super.onPause()
        RosbankApplication.INSTANCE.getNavigatorHolder()!!.removeNavigator()
    }
    override fun onDestroy() {
        super.onDestroy()
        if (isFinishing) {
            compositeDisposable.clear()
        }
    }
}