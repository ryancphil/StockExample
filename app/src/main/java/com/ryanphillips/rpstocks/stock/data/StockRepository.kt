package com.ryanphillips.rpstocks.stock.data

import com.ryanphillips.rpstocks.stock.data.model.PortfolioResponse

interface StockRepository {
    suspend fun getPortfolio(): PortfolioResponse?
    suspend fun getMalformedPortfolio(): PortfolioResponse?
    suspend fun getEmptyPortfolio(): PortfolioResponse?
}