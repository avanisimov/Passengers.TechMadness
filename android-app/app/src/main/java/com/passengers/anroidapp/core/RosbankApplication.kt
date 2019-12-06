package com.passengers.anroidapp.core

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen
import com.passengers.anroidapp.BuildConfig
import com.passengers.anroidapp.module.AppModule
import com.passengers.anroidapp.module.NetModule
import com.passengers.anroidapp.module.RepoModule
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router
import timber.log.Timber
import toothpick.Toothpick

class RosbankApplication : Application() {

    companion object {
        lateinit var INSTANCE: RosbankApplication
        const val APP_SCOPE_KEY = "APP_SCOPE_KEY"
        const val SCOPE_KEY = "SCOPE_KEY"
    }

    private var cicerone: Cicerone<Router>? = null


    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
        initLogging()
        initCicerone()
        initToothpick()
        initThreeten()
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

    private fun initThreeten() {
        AndroidThreeTen.init(this)
    }

    private fun initCicerone() {
        cicerone = Cicerone.create()
    }


    private fun initToothpick() {
        val openScope = Toothpick.openScope(APP_SCOPE_KEY)
        openScope.installModules(
                AppModule(this),
                NetModule(),
                RepoModule()
        )
    }

    fun getNavigatorHolder(): NavigatorHolder? {
        return cicerone!!.navigatorHolder
    }

    fun getRouter(): Router? {
        return cicerone!!.router
    }
}
