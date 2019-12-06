package com.passengers.mobileapi.service

import com.passengers.mobileapi.endpoint.PushTokenPlatform
import org.springframework.stereotype.Service

@Service
class PushTokenService {
    fun savePushToken(userId: String, pushToken: String, platform: PushTokenPlatform) {

    }
}