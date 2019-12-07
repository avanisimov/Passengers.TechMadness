package com.passengers.mobileapi.repository

import com.passengers.mobileapi.endpoint.PushTokenPlatform
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.Id

@Entity
class ClientsToken(
    @Id
    val id: UUID = UUID.randomUUID(),
    val clientId: Long = -1,
    val token: String? = null,
    @Enumerated(EnumType.ORDINAL)
    val platform: PushTokenPlatform
)


interface ClientsTokenRepository : JpaRepository<ClientsToken, UUID> {

}