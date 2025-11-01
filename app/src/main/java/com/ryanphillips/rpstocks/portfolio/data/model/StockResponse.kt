package com.ryanphillips.rpstocks.portfolio.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class StockResponse(
    @field:Json("ticker") val ticker: String,
    @field:Json("name") val name: String,
    @field:Json("currency") val currency: String,
    @field:Json("current_price_cents") val currentPriceCents: Int,
    @field:Json("quantity") val quantity: Int?,
    @field:Json("current_price_timestamp") val currentPriceTimestamp: Int
)
