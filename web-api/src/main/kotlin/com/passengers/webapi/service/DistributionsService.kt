package com.passengers.webapi.service

import com.passengers.webapi.data.CampaignsRepository
import com.passengers.webapi.data.ChannelType
import com.passengers.webapi.data.ClientsRepository
import com.passengers.webapi.data.DistributionsRepository
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import java.time.ZonedDateTime
import java.util.*

@Service
class DistributionsService(
    val distributionsRepository: DistributionsRepository,
    val campaignsRepository: CampaignsRepository,
    val fcmPushService: FcmPushService
) {
    @Scheduled(fixedRate = 5000)
    fun handleDistributions() {
        val dateStart = Date()
        val dateEnd = Date.from(ZonedDateTime.now().plusSeconds(5).toInstant())
        val pushDstributions = distributionsRepository.findByTypeBetweenDates(
            ChannelType.PUSH, dateStart, dateEnd
        )
        println("${Date()} handleDistributions $dateStart $dateEnd ${pushDstributions.size}")
        pushDstributions.groupBy {
            it.campaignId
        }.forEach { campaignId, distributionsByCampaign ->
            val campaignOpt = campaignsRepository.findById(campaignId)
            if (campaignOpt.isPresent) {
                val campaign = campaignOpt.get()
                if (campaign.pushChannel?.enabled == true) {
                    val clientIds = distributionsByCampaign.map { d -> d.clientId }
                    fcmPushService.sendDistributions(clientIds, campaign)
                }
            }
        }
    }
}