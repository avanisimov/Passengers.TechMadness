package com.passengers.mobileapi.service

import com.passengers.mobileapi.endpoint.PushTokenPlatform
import com.passengers.mobileapi.repository.ClientsToken
import com.passengers.mobileapi.repository.ClientsTokenRepository
import org.springframework.stereotype.Service

@Service
class PushTokenService(
    val clientsTokenRepository: ClientsTokenRepository
) {

    fun savePushToken(userId: Long, pushToken: String, platform: PushTokenPlatform) {
        clientsTokenRepository.save(
            ClientsToken(
                clientId = userId,
                token = pushToken,
                platform = platform
            )
        )
    }
}