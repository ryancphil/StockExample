package com.ryanphillips.rpstocks.portfolio.data

import com.ryanphillips.rpstocks.core.domain.DataError
import com.ryanphillips.rpstocks.core.domain.Result
import com.ryanphillips.rpstocks.core.domain.map
import com.ryanphillips.rpstocks.core.data.safeCall
import com.ryanphillips.rpstocks.portfolio.data.model.toPortfolioDto
import com.ryanphillips.rpstocks.portfolio.domain.PortfolioDto
import com.ryanphillips.rpstocks.portfolio.domain.PortfolioRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PortfolioRepositoryImpl
@Inject constructor(
    private val portfolioService: PortfolioService
) : PortfolioRepository {

    override suspend fun getPortfolio(): Flow<Result<PortfolioDto, DataError.Network>> {
        return flow {
            val result = safeCall {
                portfolioService.getPortfolio()
            }.map {
                it.toPortfolioDto()
            }
            emit(result)
        }
    }

    override suspend fun getMalformedPortfolio(): Flow<Result<PortfolioDto, DataError.Network>> {
        return flow {
            val result = safeCall {
                portfolioService.getMalformedPortfolio()
            }.map {
                it.toPortfolioDto()
            }
            emit(result)
        }
    }

    override suspend fun getEmptyPortfolio(): Flow<Result<PortfolioDto, DataError.Network>> {
        return flow {
            val result = safeCall {
                portfolioService.getEmptyPortfolio()
            }.map {
                it.toPortfolioDto()
            }
            emit(result)
        }
    }
}