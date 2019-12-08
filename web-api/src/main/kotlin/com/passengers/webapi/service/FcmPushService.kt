package com.passengers.webapi.service

import com.passengers.webapi.data.Campaign
import org.springframework.stereotype.Service
import java.util.logging.Level.SEVERE
import java.io.IOException
import com.google.firebase.FirebaseApp
import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseOptions
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.Message
import com.google.firebase.messaging.Notification
import com.passengers.webapi.FcmSettings
import com.passengers.webapi.data.ClientsTokenRepository
import java.nio.file.Files
import java.nio.file.Paths
import java.util.logging.Level
import java.util.logging.Logger


@Service
class FcmPushService(
    val fcmSettings: FcmSettings,
    val clientsTokenRepository: ClientsTokenRepository
) {
    init {
        val p = Paths.get(fcmSettings.serviceAccountFile)
        try {
            Files.newInputStream(p).use { serviceAccount ->
                val options = FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .build()

                FirebaseApp.initializeApp(options)
            }
        } catch (e: IOException) {
            Logger.getLogger(FcmPushService::class.java.simpleName)
                .log(Level.SEVERE, null, e)
        }

    }

    fun sendDistributions(
        clientIds: List<Long>,
        campaign: Campaign
    ) {
        clientIds.forEach { clientId ->
            val tokens = clientsTokenRepository.findByClientUid(clientId).map { clientsToken -> clientsToken.token!! }
            val title = campaign.title
            val body = campaign.simpleDescription
            tokens.forEach { token ->
                val message = Message.builder()
                    .setToken(token)
                    .setNotification(Notification(title, body))
                    .build()
                FirebaseMessaging
                    .getInstance()
                    .sendAsync(message)
            }
        }
    }
}