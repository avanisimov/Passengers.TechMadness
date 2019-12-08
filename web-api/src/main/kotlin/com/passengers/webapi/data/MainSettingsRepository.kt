package com.passengers.webapi.data

import java.util.*
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "")
data class MainSettings(
    @Id
    val id: Long = ID,
    val lastDistributionDate: Date = Date()
) {
    companion object {
        val ID = 123L
    }
}