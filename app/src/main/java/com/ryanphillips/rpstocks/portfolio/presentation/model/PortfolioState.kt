package com.ryanphillips.rpstocks.portfolio.presentation.model

data class PortfolioState(
    val isLoading: Boolean = true,
    val isError: Boolean = false,
    val stocks: List<StockState> = emptyList()
)