package com.passengers.anroidapp.network.model

import com.google.gson.annotations.SerializedName

enum class FeedItemType {
    /**
     * News
     */
    @SerializedName("1")
    NEWS,
    /**
     * SpecialDeal
     */
    @SerializedName("2")
    SPECIAL_DEAL
}