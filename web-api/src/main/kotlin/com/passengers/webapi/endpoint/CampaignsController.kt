package com.passengers.webapi.endpoint

import com.passengers.webapi.data.Campaign
import com.passengers.webapi.service.CampaignCreateForm
import com.passengers.webapi.service.CampaignsService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
class CampaignsController(
    val campaignsService: CampaignsService
) {

    @PostMapping("campaigns")
    fun postCampaign(
        @RequestBody campaignCreateForm: CampaignCreateForm
    ): ResponseEntity<Campaign> {
        return ResponseEntity.ok(
            campaignsService.createCampaign(campaignCreateForm)
        )
    }

    @GetMapping("campaigns")
    fun getCampaigns(): ResponseEntity<CampaignListResponse> {
        return ResponseEntity.ok(
            CampaignListResponse(
                0,
                emptyList()
            )
        )
    }
}

data class CampaignListResponse(
    val total: Int,
    val items: List<CampaignShort>
)

data class CampaignShort(
    val id: UUID
)