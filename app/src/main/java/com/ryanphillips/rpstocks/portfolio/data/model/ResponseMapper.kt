package com.ryanphillips.rpstocks.portfolio.data.model

import com.ryanphillips.rpstocks.portfolio.domain.PortfolioDto
import com.ryanphillips.rpstocks.portfolio.domain.StockDto

fun PortfolioResponse.toPortfolioDto(): PortfolioDto {
    return PortfolioDto(
        stocks = this.stocks.map {
            it.toStockDto()
        }
    )
}

fun PortfolioDto.toPortfolioResponse(): PortfolioResponse {
    return PortfolioResponse(
        stocks = stocks.map {
            it.toStockResponse()
        }
    )
}

fun StockResponse.toStockDto(): StockDto {
    return StockDto(
        symbol = ticker,
        name = name,
        currency = currency,
        currentPriceCents = currentPriceCents,
        quantity = quantity,
        timestamp = currentPriceTimestamp
    )
}

fun StockDto.toStockResponse(): StockResponse {
    return StockResponse(
        ticker = symbol,
        name = name,
        currency = currency,
        currentPriceCents = currentPriceCents,
        quantity = quantity,
        currentPriceTimestamp = timestamp
    )
}