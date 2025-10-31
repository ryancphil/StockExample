package com.ryanphillips.rpstocks.stock.ui.model

data class StockState(
    val symbol: String,
    val name: String,
    val currency: String,
    val price: String,
    val amount: String
)