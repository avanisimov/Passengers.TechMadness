package com.passengers.webapi.endpoint

import com.passengers.webapi.data.Audience
import com.passengers.webapi.service.AudienceCreateForm
import com.passengers.webapi.service.AudienceFull
import com.passengers.webapi.service.AudiencesListResponse
import com.passengers.webapi.service.AudiencesService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.UUID
import javax.websocket.server.PathParam

@RestController
class AudiencesController(
    val audiencesService: AudiencesService
) {

    @GetMapping("audiences")
    fun getAudiences(
        @RequestParam("skip", required = false)
        skip: Int?,
        @RequestParam("take", required = false)
        take: Int?
    ): ResponseEntity<AudiencesListResponse> {
        return ResponseEntity.ok(
            audiencesService.getAudiences(skip ?: 0, take ?: 20)
        )
    }

    @PostMapping("audiences")
    fun postAudience(
        @RequestBody(required = true)
        audienceCreateForm: AudienceCreateForm
    ): ResponseEntity<AudienceFull> {
        return ResponseEntity.ok(
            audiencesService.createAudience(audienceCreateForm)
        )
    }

    @GetMapping("audiences/{id}")
    fun getAudiences(
        @PathVariable("id")
        id: String
    ): ResponseEntity<AudienceFull> {
        return ResponseEntity.ok(
            audiencesService.getAudience(id)
        )
    }

    @GetMapping("clients")
    fun getClients(
        @RequestParam("audienceId")
        audienceId: String
    ):ResponseEntity<Any> {
        return ResponseEntity.ok(
            audiencesService.getClients(UUID.fromString(audienceId))
        )
    }
}