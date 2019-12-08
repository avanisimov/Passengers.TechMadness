package com.passengers.webapi.data

import org.springframework.data.jpa.repository.JpaRepository
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "clients_tokens")
class ClientsToken(
    @Id
    val id: UUID = UUID.randomUUID(),
    val clientUid: Long = -1,
    val token: String? = null,
    @Enumerated(EnumType.ORDINAL)
    val platform: PushTokenPlatform = PushTokenPlatform.ANDROID
)


interface ClientsTokenRepository : JpaRepository<ClientsToken, UUID> {
    fun findByClientUid(clientUid: Long): List<ClientsToken>

}

enum class PushTokenPlatform {
    ANDROID
}