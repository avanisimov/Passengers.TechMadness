package com.passengers.webapi.service

import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.messaging.*
import com.passengers.webapi.data.Campaign
import com.passengers.webapi.data.ClientsTokenRepository
import org.springframework.stereotype.Service
import java.io.FileInputStream
import java.io.IOException
import java.util.logging.Level
import java.util.logging.Logger
import com.google.firebase.messaging.Aps
import com.google.firebase.messaging.ApnsConfig
import com.google.firebase.messaging.AndroidNotification
import com.google.firebase.messaging.AndroidConfig
import org.apache.http.entity.BasicHttpEntity
import org.springframework.http.*
import org.springframework.web.client.RestTemplate
import java.util.*


@Service
class FcmPushService(
    val clientsTokenRepository: ClientsTokenRepository
) {
    companion object {
        var wasInit = false
    }

    init {
        if (!wasInit) {
            wasInit = true
            try {
                val serviceAccount =
                    FileInputStream("/Users/avanisimov/Downloads/techmadness-d7c60-firebase-adminsdk-q4e3o-6f66453ff0.json")

                val options = FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .setDatabaseUrl("https://techmadness-d7c60.firebaseio.com")
                    .build()

                FirebaseApp.initializeApp(options)
                println("FirebaseApp.initializeApp(options) after")

            } catch (e: IOException) {
                Logger.getLogger(FcmPushService::class.java.simpleName)
                    .log(Level.SEVERE, null, e)
            }
        }
    }

    fun sendDistributions(
        clientIds: List<Long>,
        campaign: Campaign
    ) {
        clientIds.forEach { clientId ->
            val tokens = clientsTokenRepository.findByClientUid(clientId)
                .map { clientsToken -> clientsToken.token!! }
                .distinct()
            val title = campaign.title
            val body = campaign.simpleDescription.replace("{client_name}", "User@$clientId")
            println("FcmPushService sendDistributions $tokens $title $body")
            tokens.forEach { token ->
                //                val message = Message.builder()
//                    .setToken(token)
//                    .setAndroidConfig(
//                        AndroidConfig.builder()
//                            .setNotification(
//                                AndroidNotification.builder()
//                                    .setTitle(title)
//                                    .setBody(body)
//                                    .build()
//                            )
//                            .build()
//                    )
//                    .setNotification(Notification(title, body))
//                    .build()
//                val message = Message.builder()
//                    .setNotification(
//                        Notification(
//                            title,
//                            body
//                        )
//                    )
//                    .setAndroidConfig(
//                        AndroidConfig.builder()
//                            .setTtl((3600 * 1000).toLong())
//                            .setNotification(
//                                AndroidNotification.builder()
//                                    .setIcon("stock_ticker_update")
//                                    .setColor("#f45342")
//                                    .build()
//                            )
//                            .build()
//                    )
//                    .setToken(token)
//                    .build()
//                val result = FirebaseMessaging
//                    .getInstance()
//                    .send(message)
//                println("FcmPushService send(message) $result")


//                https://fcm.googleapis.com/fcm/send
//                Content-Type:application/json
//                Authorization:key=AIzaSyZ-1u...0GBYzPu7Udno5aA
                val restTemplate = RestTemplate()
                val headers = HttpHeaders()
                headers.add(
                    "Content-Type",
                    "application/json;charset=utf-8"
                )
                headers.add(
                    "Authorization",
                    "key=AAAANMEtqWc:APA91bG14nWissdt9fxe-rU3v27MdtLQVQL1eObyyy2hjrJdnLoGGj1bI4KRgkfEk-tQMG9YchO1xLtkPZlGBkdKmEEa40C6tLqIgYlGITY8uZXCFzTwOir39emjiQLjocaM-t9aYIBh"
                )
                val fcmBody = "{ \"data\": {\n" +
                        "    \"campaignId\": \"${campaign.id}\"\n" +
                        "  },\n" +
                        " \"notification\": {\n" +
                        "    \"title\": \"$title\",\n" +
                        "    \"body\": \"$body\"\n" +
                        "  },\n" +
                        "  \"to\" : \"$token\"\n" +
                        "}"
                println(fcmBody)
                val entity = HttpEntity<String>(
                    fcmBody, headers
                )

                val s = restTemplate.exchange(
                    "https://fcm.googleapis.com/fcm/send",
                    HttpMethod.POST,
                    entity,
                    String::class.java
                )
                println(s.body!!.toString())
            }
        }
    }
}