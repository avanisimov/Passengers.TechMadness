package com.passengers.anroidapp.network.api

import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface RosbankApi {

    @POST("/token")
    fun getUser(@Body any: Any?): Single<Any?>
}

