package com.passengers.anroidapp.module

import com.google.gson.Gson
import com.passengers.anroidapp.network.api.RosbankApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import toothpick.config.Module
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Provider

class NetModule : Module() {


    internal class OkHttpClientProvider : Provider<OkHttpClient> {
        @Inject
        override fun get(): OkHttpClient {
            val TIMEOUT_DURATION = 30L
            return OkHttpClient.Builder()
                    .addInterceptor(HttpLoggingInterceptor())
                    .connectTimeout(TIMEOUT_DURATION, TimeUnit.SECONDS)
                    .readTimeout(TIMEOUT_DURATION, TimeUnit.SECONDS)
                    .writeTimeout(TIMEOUT_DURATION, TimeUnit.SECONDS)
                    .build()
        }
    }

    internal class ApiProvider : Provider<RosbankApi> {
        @Inject
        var gson: Gson? = null
        @Inject
        var okHttpClient: OkHttpClient? = null

        override fun get(): RosbankApi {
            return Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(okHttpClient)
                    .baseUrl("http://167.71.48.207:5555/")
                    .build()
                    .create<RosbankApi>(RosbankApi::class.java)
        }
    }

    init {
        bind(OkHttpClient::class.java).toProvider(OkHttpClientProvider::class.java)
        bind<RosbankApi>(RosbankApi::class.java).toProvider(ApiProvider::class.java).singleton()
    }
}