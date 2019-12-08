package com.passengers.anroidapp.core

import android.app.Application
import com.google.gson.GsonBuilder
import com.jakewharton.threetenabp.AndroidThreeTen
import com.passengers.anroidapp.BuildConfig
import com.passengers.anroidapp.feature.news.NewsViewModel
import com.passengers.anroidapp.network.api.RosbankApi
import com.passengers.anroidapp.network.repo.news.NewsRepository
import com.passengers.anroidapp.network.repo.news.NewsRepositoryMock
import com.passengers.anroidapp.network.repo.push.PushRepository
import com.passengers.anroidapp.network.repo.push.PushRepositoryImpl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router
import timber.log.Timber
import java.util.concurrent.TimeUnit

class RosbankApplication : Application() {

    companion object {
        lateinit var INSTANCE: RosbankApplication
        const val APP_SCOPE_KEY = "APP_SCOPE_KEY"
        const val SCOPE_KEY = "SCOPE_KEY"
        const val TIMEOUT_DURATION = 30L
    }

    private var cicerone: Cicerone<Router>? = null


    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
        initLogging()
        initCicerone()
        initDI()
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


    private fun initDI() {
        val modules = listOf(

                module {
                        single { GsonBuilder().create() }
                },

                module {
                    single {
                        OkHttpClient.Builder()
                                .addInterceptor(HttpLoggingInterceptor().apply {
                                    level = HttpLoggingInterceptor.Level.BODY
                                })
                                .connectTimeout(TIMEOUT_DURATION, TimeUnit.SECONDS)
                                .readTimeout(TIMEOUT_DURATION, TimeUnit.SECONDS)
                                .writeTimeout(TIMEOUT_DURATION, TimeUnit.SECONDS)
                                .build()
                    }

                    single {
                        Retrofit.Builder()
                                .addConverterFactory(GsonConverterFactory.create(get()))
                                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                                .client(get())
                                .baseUrl("http://167.71.48.207:5555/api/mobile/")
                                .build()
                                .create<RosbankApi>(RosbankApi::class.java)
                    }
                },

                module {
                    single<NewsRepository>{
                        NewsRepositoryMock()
                    }
                    single<PushRepository> {
                        PushRepositoryImpl(get())
                    }
                },

                module {
                    viewModel {
                        NewsViewModel(get())
                    }
                }

        )

        startKoin {
            androidContext(this@RosbankApplication)
            modules(modules)
        }
    }

    fun getNavigatorHolder(): NavigatorHolder? {
        return cicerone!!.navigatorHolder
    }

    fun getRouter(): Router? {
        return cicerone!!.router
    }
}
