package com.passengers.webapi.service

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.passengers.webapi.data.Audience
import com.passengers.webapi.data.AudiencesRepository
import com.passengers.webapi.data.Client
import com.passengers.webapi.data.ClientsRepository
import javassist.NotFoundException
import org.springframework.data.jpa.domain.Specification
import org.springframework.data.jpa.domain.Specifications
import org.springframework.stereotype.Service
import java.lang.StringBuilder
import java.util.*
import javax.persistence.criteria.Root

@Service
class AudiencesService(
    val audiencesRepository: AudiencesRepository,
    val clientsRepository: ClientsRepository
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
                productsCount = audienceCreateForm.productsCount,
                totalAmountMin = audienceCreateForm.totalAmountMin,
                totalAmountMax = audienceCreateForm.totalAmountMax,
                rfmSegments = objectMapper.writeValueAsString(audienceCreateForm.rfmSegments)
            )
        )
        return AudienceFull(
            audience.id,
            audience.title,
            jacksonObjectMapper().readValue<List<String>>(audience.regions ?: "[]"),
            audience.registeredMonthsAgo,
            audience.lastPaymentMonthsAgo,
            audience.transactionsCountMin,
            audience.productsCount,
            audience.totalAmountMin,
            audience.totalAmountMax
        )
    }

    fun getAudiences(skip: Int, take: Int): AudiencesListResponse {
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

    fun getAudience(id: String): AudienceFull {
        val findById = audiencesRepository.findById(UUID.fromString(id))
        if (findById.isPresent) {
            val audience = findById.get()
            return AudienceFull(
                audience.id,
                audience.title,
                jacksonObjectMapper().readValue<List<String>>(audience.regions ?: "[]"),
                audience.registeredMonthsAgo,
                audience.lastPaymentMonthsAgo,
                audience.transactionsCountMin,
                audience.productsCount,
                audience.totalAmountMin,
                audience.totalAmountMax,
                jacksonObjectMapper().readValue<List<String>>(audience.rfmSegments ?: "[]")
            )
        }
        throw NotFoundException("Can't find Audience for id=$id")
    }


    fun getClients(audienceId: UUID): List<Client> {
        val findById = audiencesRepository.findById(audienceId)
        if (findById.isPresent) {
            val audience = findById.get()
            return clientsRepository.customQueryByAudience(
                jacksonObjectMapper().readValue(audience.regions ?: "[]"),
                audience.registeredMonthsAgo ?: Int.MAX_VALUE,
                audience.lastPaymentMonthsAgo ?: Int.MAX_VALUE,
                audience.transactionsCountMin ?: 0,
                audience.productsCount ?: 0,
                audience.totalAmountMin ?:0.0,
                audience.totalAmountMax ?: Double.MAX_VALUE,
                jacksonObjectMapper().readValue(audience.rfmSegments ?: "[]")
            )
        }
        throw NotFoundException("ololo")
    }
}

data class AudienceCreateForm(
    val title: String = "",
    val regions: List<String>? = null,
    val registeredMonthsAgo: Int? = null,
    val lastPaymentMonthsAgo: Int? = null,
    val transactionsCountMin: Int? = null,
    val productsCount: Int? = null,
    val totalAmountMin: Double? = null,
    val totalAmountMax: Double? = null,
    val rfmSegments: List<String>? = null
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
    val productsCount: Int? = null,
    val totalAmountMin: Double? = null,
    val totalAmountMax: Double? = null,
    val rfmSegments: List<String>? = null
)