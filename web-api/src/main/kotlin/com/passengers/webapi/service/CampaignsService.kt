package com.passengers.webapi.service

import com.passengers.webapi.data.Campaign
import com.passengers.webapi.data.CampaignsRepository
import com.passengers.webapi.data.Channel
import org.springframework.stereotype.Service
import java.util.*

@Service
class CampaignsService(
    val campaignsRepository: CampaignsRepository
) {

    fun createCampaign(campaignCreateForm: CampaignCreateForm): Campaign {
        return campaignsRepository.save(
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