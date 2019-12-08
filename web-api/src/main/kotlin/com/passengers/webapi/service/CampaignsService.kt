package com.passengers.webapi.service

import com.passengers.webapi.data.*
import org.springframework.stereotype.Service
import java.util.*

@Service
class CampaignsService(
    val campaignsRepository: CampaignsRepository,
    val audiencesService: AudiencesService,
    val distributionsRepository: DistributionsRepository
) {

    fun createCampaign(campaignCreateForm: CampaignCreateForm): Campaign {
        val campaign = campaignsRepository.save(
            Campaign(
                title = campaignCreateForm.title,
                simpleDescription = campaignCreateForm.simpleDescription,
                fullDescription = campaignCreateForm.fullDescription,
                audienceId = UUID.fromString(campaignCreateForm.audienceId),
                pushChannel = campaignCreateForm.pushChannel,
                smsChannel = campaignCreateForm.smsChannel,
                emailChannel = campaignCreateForm.emailChannel,
                chatChannel = campaignCreateForm.chatChannel
            )
        )

        val clients = audiencesService.getClients(audienceId = campaign.audienceId)
        val distributions = mutableListOf<Distribution>()
        clients.forEach {client ->
            if (campaign.pushChannel?.enabled == true) {
                distributions.add(
                    Distribution(
                        UUID.randomUUID(),
                        campaign.id,
                        client.uid,
                        ChannelType.PUSH,
                        campaign.pushChannel.startAt
                    )
                )
            }
            if (campaign.pushChannel?.enabled == true) {
                distributions.add(
                    Distribution(
                        UUID.randomUUID(),
                        campaign.id,
                        client.uid,
                        ChannelType.SMS,
                        campaign.pushChannel.startAt
                    )
                )
            }
            if (campaign.pushChannel?.enabled == true) {
                distributions.add(
                    Distribution(
                        UUID.randomUUID(),
                        campaign.id,
                        client.uid,
                        ChannelType.EMAIL,
                        campaign.pushChannel.startAt
                    )
                )
            }
            if (campaign.pushChannel?.enabled == true) {
                distributions.add(
                    Distribution(
                        UUID.randomUUID(),
                        campaign.id,
                        client.uid,
                        ChannelType.CHAT,
                        campaign.pushChannel.startAt
                    )
                )
            }
        }

        distributionsRepository.saveAll(distributions)
        return campaign
    }
}

data class CampaignCreateForm(
    val title: String,
    val simpleDescription: String,
    val fullDescription: String,
    val audienceId: String,
    val pushChannel: Channel,
    val smsChannel: Channel,
    val emailChannel: Channel,
    val chatChannel: Channel
)