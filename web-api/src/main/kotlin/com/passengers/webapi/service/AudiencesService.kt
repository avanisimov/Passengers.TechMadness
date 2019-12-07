package com.passengers.webapi.service

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.passengers.webapi.data.Audience
import com.passengers.webapi.data.AudiencesRepository
import org.springframework.stereotype.Service
import java.lang.StringBuilder
import java.util.*

@Service
class AudiencesService(
    val audiencesRepository: AudiencesRepository
) {

    fun createAudience(audienceCreateForm: AudienceCreateForm): AudienceFull {
        val objectMapper = ObjectMapper()
        val audience = audiencesRepository.save(
            Audience(
                title = audienceCreateForm.title,
                description = audienceCreateForm.createDescription(),
                regions = objectMapper.writeValueAsString(audienceCreateForm.regions),
                registeredMonthsAgo = audienceCreateForm.registeredMonthsAgo,
                lastPaymentMonthsAgo = audienceCreateForm.lastPaymentMonthsAgo,
                transactionsCountMin = audienceCreateForm.transactionsCountMin,
                transactionsCountMax = audienceCreateForm.transactionsCountMax,
                productsCount = audienceCreateForm.productsCount,
                totalAmountMin = audienceCreateForm.totalAmountMin,
                totalAmountMax = audienceCreateForm.totalAmountMax
            )
        )
        return AudienceFull(
            audience.id,
            audience.title,
            jacksonObjectMapper().readValue<List<String>>(audience.regions ?: "[]"),
            audience.registeredMonthsAgo,
            audience.lastPaymentMonthsAgo,
            audience.transactionsCountMin,
            audience.transactionsCountMax,
            audience.productsCount,
            audience.totalAmountMin,
            audience.totalAmountMax
        )
    }

    fun getAudiences(skip: Int, take: Int): AudiencesListResponse {
//        val items = mutableListOf<AudienceShort>()
//        items.add(
//            AudienceShort(
//                UUID.randomUUID().toString(),
//                "Малый бизнес",
//                "Доходы: от 10.000 до 50.000, лалалалла тарататататататата олололол"
//            )
//        )
//        items.add(
//            AudienceShort(
//                UUID.randomUUID().toString(),
//                "Средний бизнес",
//                "Доходы: от 50.000 до 50.001, лалалалла тарататататататата олололол"
//            )
//        )
//        items.add(
//            AudienceShort(
//                UUID.randomUUID().toString(),
//                "Сварщики",
//                "лалалалла тарататататататата олололол"
//            )
//        )
//        items.add(
//            AudienceShort(
//                UUID.randomUUID().toString(),
//                "Мастера по ноготочкам",
//                "лалалалла тарататататататата олололол"
//            )
//        )
        val items = audiencesRepository
            .findAll()
            .map { audience ->
                AudienceShort(
                    audience.id.toString(),
                    audience.title,
                    audience.description
                )
            }

        return AudiencesListResponse(
            items.size,
            items
        )
    }

}

data class AudienceCreateForm(
    val title: String = "",
    val regions: List<String>? = null,
    val registeredMonthsAgo: Int? = null,
    val lastPaymentMonthsAgo: Int? = null,
    val transactionsCountMin: Int? = null,
    val transactionsCountMax: Int? = null,
    val productsCount: Int? = null,
    val totalAmountMin: Int? = null,
    val totalAmountMax: Int? = null
)

fun AudienceCreateForm.createDescription(): String {
    val sb = StringBuilder()
    regions?.let {
        sb.append("Регионы : ")
        it.forEachIndexed { index, region ->
            if (index > 0) {
                sb.append(", $region")
            } else {
                sb.append(region)
            }
        }
    }
    return sb.toString()
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

data class AudienceFull(
    val id: UUID = UUID.randomUUID(),
    val title: String? = null,
    val regions: List<String>? = null,
    val registeredMonthsAgo: Int? = null,
    val lastPaymentMonthsAgo: Int? = null,
    val transactionsCountMin: Int? = null,
    val transactionsCountMax: Int? = null,
    val productsCount: Int? = null,
    val totalAmountMin: Int? = null,
    val totalAmountMax: Int? = null
)