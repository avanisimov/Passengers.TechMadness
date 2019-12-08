package com.passengers.anroidapp.network.model

import com.google.gson.annotations.SerializedName
import org.threeten.bp.LocalDateTime

data class FeedItem(

        @SerializedName("id")
        var id: String,

        @SerializedName("title")
        val title: String,

        @SerializedName("simpleDescription")
        var simpleDescription: String,

        @SerializedName("fullDescription")
        var fullDescription: String,

        @SerializedName("audienceId")
        var audienceId: String,

        @SerializedName("date")
        val date: LocalDateTime? = null,

        @SerializedName("type")
        val type: FeedItemType? = null,

        @SerializedName("thumbnail")
        val imageUrl: String? = null,

        @SerializedName("callContacts")
        val callContacts: List<String>? = null,

        @SerializedName("endDate")
        var endDate: LocalDateTime? = null,

        @SerializedName("author")
        var authorName: String? = null,

        @SerializedName("isRead")
        var isRead: Boolean? = null
)