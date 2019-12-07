package com.passengers.anroidapp.module

import android.content.Context
import android.content.res.Resources
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import toothpick.config.Module
import javax.inject.Inject
import javax.inject.Provider

class AppModule(applicationContext: Context) : Module() {

    internal class GsonProvider : Provider<Gson> {
        @Inject
        override fun get(): Gson {
            return GsonBuilder().create()
        }
    }

    init {
        bind(Context::class.java).toInstance(applicationContext)
        bind(Resources::class.java).toInstance(applicationContext.resources)
        bind(Gson::class.java).toProvider(GsonProvider::class.java).singleton()
    }
}