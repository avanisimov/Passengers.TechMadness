package com.passengers.webapi.service

import com.passengers.webapi.data.CampaignsRepository
import com.passengers.webapi.data.ClientsRepository
import com.passengers.webapi.data.DistributionsRepository
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import java.time.ZonedDateTime
import java.util.*

@Service
class DistributionsService(
    val distributionsRepository: DistributionsRepository,
    val clientsRepository: ClientsRepository,
    val campaignsRepository: CampaignsRepository,
    val fcmPushService: FcmPushService
) {
    @Scheduled(fixedRate = 5000)
    fun handleDistributions() {
        val dateStart = Date()
        val dateEnd = Date.from(ZonedDateTime.now().plusMinutes(5).toInstant())
        val distributions = distributionsRepository.findBetweenDates(
            dateStart, dateEnd
        )
        distributions.groupBy {
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