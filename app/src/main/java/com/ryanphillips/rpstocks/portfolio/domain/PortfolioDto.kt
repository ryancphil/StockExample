package com.ryanphillips.rpstocks.portfolio.domain

data class PortfolioDto(
    val stocks: List<StockDto> = emptyList()
)
