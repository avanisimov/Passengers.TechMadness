package com.passengers.webapi.data

import org.springframework.data.jpa.repository.JpaRepository
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "campaign_to_client_distribution")
data class Distribution(
    @Id
    val id: UUID = UUID.randomUUID(),
    val campaignId: UUID = UUID.randomUUID(),
    val clientId: Long = -1,
    @Enumerated(EnumType.STRING)
    val channelType: ChannelType = ChannelType.PUSH,
    val startAt: Date = Date()
)

interface DistributionsRepository : JpaRepository<Distribution, UUID>

enum class ChannelType {
    PUSH, SMS, EMAIL, CHAT
}