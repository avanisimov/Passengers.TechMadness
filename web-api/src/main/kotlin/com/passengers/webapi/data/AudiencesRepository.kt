package com.passengers.webapi.data

import org.springframework.data.jpa.repository.JpaRepository
import java.util.*
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "audiences")
data class Audience(
    @Id
    val id: UUID = UUID.randomUUID(),
    val title: String = "",
    val description: String = "",
    val regions: String? = null,
    val registeredMonthsAgo: Int? = null,
    val lastPaymentMonthsAgo: Int? = null,
    val transactionsCountMin: Int? = null,
    val transactionsCountMax: Int? = null,
    val productsCount: Int? = null,
    val totalAmountMin: Int? = null,
    val totalAmountMax: Int? = null
)

interface AudiencesRepository : JpaRepository<Audience, UUID>