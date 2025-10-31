package com.ryanphillips.rpstocks.stock.ui.model

data class PortfolioScreenState(
    val isLoading: Boolean = true,
    val isError: Boolean = false,
    val stocks: List<StockState> = emptyList()
)