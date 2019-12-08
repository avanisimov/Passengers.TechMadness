package com.passengers.anroidapp.network.repo.push

import com.passengers.anroidapp.network.api.RosbankApi
import com.passengers.anroidapp.network.model.PushToken
import io.reactivex.Completable
import io.reactivex.schedulers.Schedulers

class PushRepositoryImpl (private val rosbankApi: RosbankApi) : PushRepository {

    override fun pushToken(token: PushToken): Completable {
        return rosbankApi
                .postToken(token)
                .subscribeOn(Schedulers.io())
    }
}
