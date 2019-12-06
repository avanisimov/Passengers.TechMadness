package com.passengers.anroidapp

import android.app.Application

import timber.log.Timber

class RosbankApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initLogging()
    }

    private fun initLogging() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        } else {
            Timber.plant(object : Timber.Tree() {
                override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
                    // don't log in not DEBUG build
                }
            })
        }
    }
}
