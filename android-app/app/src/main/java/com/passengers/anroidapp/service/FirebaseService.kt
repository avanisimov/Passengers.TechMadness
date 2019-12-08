package com.passengers.anroidapp.service

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.passengers.anroidapp.network.model.PushToken
import com.passengers.anroidapp.network.model.PushTokenPlatform
import com.passengers.anroidapp.network.repo.MockCollection
import com.passengers.anroidapp.network.repo.push.PushRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Action
import timber.log.Timber

class FirebaseService(private val pushRepository: PushRepository) : FirebaseMessagingService() {


    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
    }

    override fun onNewToken(s: String) {
        super.onNewToken(s)

        pushRepository
                .pushToken(PushToken(MockCollection.userID, s, PushTokenPlatform.ANDROID))
                .observeOn(AndroidSchedulers.mainThread())
                .doOnComplete {
                    Timber.i("PushToken sended, token = %s", s)
                }
                .subscribe()
    }
}