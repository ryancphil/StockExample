package com.ryanphillips.rpstocks.portfolio.presentation.model

data class StockState(
    val symbol: String,
    val name: String,
    val currency: String,
    val price: String,
    val amount: String,
    val timestamp: String
)