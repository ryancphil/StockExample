package com.ryanphillips.rpstocks.portfolio.domain

data class StockDto(
    val symbol: String,
    val name: String,
    val currency: String,
    val currentPriceCents: Int,
    val quantity: Int?,
    val timestamp: Int
)
