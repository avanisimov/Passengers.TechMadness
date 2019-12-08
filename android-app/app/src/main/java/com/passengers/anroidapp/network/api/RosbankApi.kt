package com.passengers.anroidapp.network.api

import com.passengers.anroidapp.network.model.PushToken
import io.reactivex.Completable
import retrofit2.http.Body
import retrofit2.http.POST

interface RosbankApi {

    @POST("token")
    fun postToken(@Body token: PushToken): Completable
}

