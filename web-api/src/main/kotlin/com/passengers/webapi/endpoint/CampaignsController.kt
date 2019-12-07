package com.passengers.webapi.endpoint

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
class CampaignsController {

    @PostMapping("campaigns")
    fun postCampaign(
        @RequestBody campaignRequestBody: CampaignRequestBody
    ): ResponseEntity<Any> {
        return ResponseEntity.noContent().build()
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

data class CampaignRequestBody(
    val title: String
)

data class CampaignListResponse(
    val total: Int,
    val items: List<CampaignShort>
)

data class CampaignShort(
    val id: UUID
)