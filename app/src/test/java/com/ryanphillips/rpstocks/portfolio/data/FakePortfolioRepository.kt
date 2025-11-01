package com.ryanphillips.rpstocks.portfolio.data

import com.ryanphillips.rpstocks.core.domain.DataError
import com.ryanphillips.rpstocks.core.domain.Result
import com.ryanphillips.rpstocks.portfolio.data.model.StockResponse
import com.ryanphillips.rpstocks.portfolio.data.model.toStockDto
import com.ryanphillips.rpstocks.portfolio.domain.PortfolioDto
import com.ryanphillips.rpstocks.portfolio.domain.PortfolioRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakePortfolioRepository: PortfolioRepository {

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

    override suspend fun getPortfolio(): Flow<Result<PortfolioDto, DataError.Network>> {
        return flow {
            val pDTO = PortfolioDto(
                stocks = stocks.map { it.toStockDto() }
            )
            emit(Result.Success(pDTO))
        }
    }

    override suspend fun getMalformedPortfolio(): Flow<Result<PortfolioDto, DataError.Network>> {
        return flow {
            emit(Result.Error(DataError.Network.UNKNOWN))
        }
    }

    override suspend fun getEmptyPortfolio(): Flow<Result<PortfolioDto, DataError.Network>> {
        return flow {
            emit(Result.Success(PortfolioDto(emptyList())))
        }
    }
}