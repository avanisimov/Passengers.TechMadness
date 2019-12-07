package com.passengers.webapi.data

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.util.*
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "clients")
data class Client(
    @Id
    val uid: Long = -1,
    val mainRegion: String = "",
    val cohort: Date = Date(),
    val lastDate: Date = Date(),
    val ordersCount: Int = -1,
    val productsCount: Int = -1,
    val totalAmount: Double = Double.NaN,
    val rfmSegment: String = ""
)

interface ClientsRepository : JpaRepository<Client, Long>, JpaSpecificationExecutor<Client> {

    @Query(
        value = "SELECT uid" +
                ", main_region" +
                ", cohort" +
                ", last_date" +
                ", orders_count" +
                ", products_count" +
                ", total_amount" +
                ", rfm_segment" +
                " FROM " +
                "clients" +
                " WHERE " +
                "     main_region IN :mainRegionsArray" +
                " AND cohort >= CURRENT_DATE - (INTERVAL '1' MONTH) * :registeredMonthsAgo " +
                " AND last_date >= CURRENT_DATE - (INTERVAL '1' MONTH) * :lastPaymentMonthsAgo " +
                " AND orders_count >= :transactionsCountMin" +
                " AND products_count >= :productsCount " +
                " AND total_amount BETWEEN :totalAmountMin AND :totalAmountMax " +
                " AND rfm_segment IN :rfmSegmentsArray "
        ,
        nativeQuery = true
    )
    fun customQueryByAudience(
        @Param("mainRegionsArray") mainRegionsArray: List<String>,
        @Param("registeredMonthsAgo") registeredMonthsAgo: Int,
        @Param("lastPaymentMonthsAgo") lastPaymentMonthsAgo: Int,
        @Param("transactionsCountMin") transactionsCountMin: Int,
        @Param("productsCount") productsCount: Int,
        @Param("totalAmountMin") totalAmountMin: Double,
        @Param("totalAmountMax") totalAmountMax: Double,
        @Param("rfmSegmentsArray") rfmSegmentsArray: List<String>
    ): List<Client>
}

//SELECT
//uid
//, main_region
//, cohort
//, last_date
//, orders_count
//, products_count
//, total_amount
//FROM
//clients
//WHERE 1 = 1
//AND main_region = ANY (ARRAY ['Москва', 'Крым'])
//AND cohort <= CURRENT_DATE - INTERVAL '1' MONTH
//AND last_date <= CURRENT_DATE - INTERVAL '1' MONTH
//AND orders_count >= 100
//AND products_count >= 2
//AND total_amount BETWEEN 100 AND 1000

