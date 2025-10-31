package com.ryanphillips.rpstocks.stock.data

import com.ryanphillips.rpstocks.BuildConfig
import com.ryanphillips.rpstocks.stock.data.model.PortfolioResponse
import retrofit2.http.GET

interface StockService {
    @GET(BuildConfig.portfolio)
    suspend fun getPortfolio(): PortfolioResponse

    @GET(BuildConfig.malformed)
    suspend fun getMalformedPortfolio(): PortfolioResponse

    @GET(BuildConfig.empty)
    suspend fun getEmptyPortfolio(): PortfolioResponse
}