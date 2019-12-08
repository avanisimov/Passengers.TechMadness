package com.passengers.anroidapp.network.model

data class PushToken(
        val userId: Long,
        val pushToken: String,
        val platform: PushTokenPlatform
)

enum class PushTokenPlatform {
    ANDROID
}