package com.ryanphillips.rpstocks.stock.data

import com.ryanphillips.rpstocks.stock.data.model.PortfolioResponse
import com.ryanphillips.rpstocks.stock.data.model.StockResponse

class FakeStockRepository: StockRepository {

    private val stocks = mutableListOf<StockResponse>().apply {
        add(
            StockResponse(
                ticker = "^GSPC",
                name = "S&P 500",
                currency = "USD",
                currentPriceCents = 318157,
                quantity = null,
                currentPriceTimestamp = 1681845832
            )
        )
        add(
            StockResponse(
                ticker = "^RUNINC",
                name = "Runners Inc.",
                currency = "USD",
                currentPriceCents = 3614,
                quantity = 5,
                currentPriceTimestamp = 1681845832
            )
        )
        add(
            StockResponse(
                ticker = "BAC",
                name = "Bank of America Corporation",
                currency = "USD",
                currentPriceCents = 2393,
                quantity = 10,
                currentPriceTimestamp = 1681845832
            )
        )
    }

    override suspend fun getPortfolio(): PortfolioResponse? {
        return PortfolioResponse(stocks)
    }

    override suspend fun getMalformedPortfolio(): PortfolioResponse? {
        return null
    }

    override suspend fun getEmptyPortfolio(): PortfolioResponse? {
        return PortfolioResponse(emptyList())
    }
}