package com.passengers.anroidapp.service

import android.R
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.passengers.anroidapp.MainActivity
import com.passengers.anroidapp.network.model.PushToken
import com.passengers.anroidapp.network.model.PushTokenPlatform
import com.passengers.anroidapp.network.repo.MockCollection
import com.passengers.anroidapp.network.repo.push.PushRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import org.koin.android.ext.android.inject
import timber.log.Timber


class FirebaseService : FirebaseMessagingService() {

    val pushRepository: PushRepository  by inject()

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        remoteMessage.data
                .forEach { (k, v) ->
                    Timber.i("Key %s Value %s", k, v)
                }

        if (remoteMessage.data != null) {
            handleMessage(remoteMessage)
        }
    }

    private fun handleMessage(remoteMessage: RemoteMessage) {

        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT)
        val channelId = "Default"
        val builder: NotificationCompat.Builder = NotificationCompat.Builder(this, channelId)
                .setContentTitle(remoteMessage.notification?.title)
                .setContentText(remoteMessage.notification?.body)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
        val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId, "Default channel", NotificationManager.IMPORTANCE_DEFAULT)
            manager.createNotificationChannel(channel)
        }
        manager.notify(0, builder.build())
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