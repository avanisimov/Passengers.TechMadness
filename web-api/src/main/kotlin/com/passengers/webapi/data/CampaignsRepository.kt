package com.passengers.webapi.data

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.data.jpa.repository.JpaRepository
import java.time.ZonedDateTime
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "campaigns")
data class Campaign(
    @Id
    val id: UUID = UUID.randomUUID(),
    val title: String = "",
    val simpleDescription: String = "",
    val fullDescription: String = "",
    val audienceId: UUID = UUID.randomUUID(),
    @Convert(converter = ChannelConverter::class)
    val pushChannel: Channel? = null,
    @Convert(converter = ChannelConverter::class)
    val smsChannel: Channel? = null,
    @Convert(converter = ChannelConverter::class)
    val emailChannel: Channel? = null,
    @Convert(converter = ChannelConverter::class)
    val chatChannel: Channel? = null
)

interface CampaignsRepository : JpaRepository<Campaign, UUID>

data class Channel(
    val enabled: Boolean = false,
    val startAt: Date = Date(),
    val maxCountInHour: Int = 0
)

@Converter
class ChannelConverter : AttributeConverter<Channel, String> {
    override fun convertToDatabaseColumn(attribute: Channel?): String {
        return ObjectMapper().writeValueAsString(attribute)
    }

    override fun convertToEntityAttribute(dbData: String?): Channel {
        return ObjectMapper().readValue(dbData, Channel::class.java)
    }

}