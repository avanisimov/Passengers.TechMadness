package com.passengers.mobileapi.endpoint

import com.passengers.mobileapi.service.PushTokenService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class PushTokenController(
    val pushTokenService: PushTokenService
) {

    @PostMapping("token")
    fun postPushToken(@RequestBody request: PushTokenRequest): ResponseEntity<Any> {
        pushTokenService.savePushToken(
            request.userId,
            request.pushToken,
            request.platform
        )
        return ResponseEntity.noContent().build()
    }
}

data class PushTokenRequest(
    val userId: Long,
    val pushToken: String,
    val platform: PushTokenPlatform
)

enum class PushTokenPlatform {
    ANDROID
}