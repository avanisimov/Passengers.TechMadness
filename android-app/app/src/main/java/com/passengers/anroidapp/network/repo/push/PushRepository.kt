package com.passengers.anroidapp.network.repo.push

import com.passengers.anroidapp.network.model.PushToken
import io.reactivex.Completable

interface PushRepository {

    fun pushToken(token: PushToken): Completable
}