package com.passengers.webapi.endpoint

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
class AudiencesController {

    @GetMapping("audiences")
    fun getAudiences(): ResponseEntity<AudiencesListResponse> {
        val items = mutableListOf<AudienceShort>()
        items.add(
            AudienceShort(
                UUID.randomUUID().toString(),
                "Малый бизнес",
                "Доходы: от 10.000 до 50.000, лалалалла тарататататататата олололол"
            )
        )
        items.add(
            AudienceShort(
                UUID.randomUUID().toString(),
                "Средний бизнес",
                "Доходы: от 50.000 до 50.001, лалалалла тарататататататата олололол"
            )
        )
        items.add(
            AudienceShort(
                UUID.randomUUID().toString(),
                "Сварщики",
                "лалалалла тарататататататата олололол"
            )
        )
        items.add(
            AudienceShort(
                UUID.randomUUID().toString(),
                "Мастера по ноготочкам",
                "лалалалла тарататататататата олололол"
            )
        )
        return ResponseEntity.ok(
            AudiencesListResponse(
                items.size,
                items
            )
        )

    }
}

data class AudiencesListResponse(
    val total: Int,
    val items: List<AudienceShort>
)

data class AudienceShort(
    val id: String,
    val title: String,
    val description: String
)