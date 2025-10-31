package com.ryanphillips.rpstocks.stock.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PortfolioResponse(
    @field:Json("stocks") val stocks: List<StockResponse>
)
