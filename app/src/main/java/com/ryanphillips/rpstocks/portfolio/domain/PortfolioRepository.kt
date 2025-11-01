package com.ryanphillips.rpstocks.portfolio.domain

import com.ryanphillips.rpstocks.core.domain.DataError
import com.ryanphillips.rpstocks.core.domain.Result
import kotlinx.coroutines.flow.Flow

interface PortfolioRepository {
    suspend fun getPortfolio(): Flow<Result<PortfolioDto, DataError.Network>>
    suspend fun getMalformedPortfolio(): Flow<Result<PortfolioDto, DataError.Network>>
    suspend fun getEmptyPortfolio(): Flow<Result<PortfolioDto, DataError.Network>>
}