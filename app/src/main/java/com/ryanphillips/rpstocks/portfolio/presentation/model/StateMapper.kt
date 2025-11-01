package com.ryanphillips.rpstocks.portfolio.presentation.model

import com.ryanphillips.rpstocks.portfolio.domain.StockDto

fun StockDto.toStockState(): StockState {
    return StockState(
        symbol = symbol,
        name = name,
        currency = currency,
        price = currentPriceCents.formatCents(),
        amount = quantity?.toString() ?: "-",
        timestamp = timestamp.formatTimestamp()
    )
}