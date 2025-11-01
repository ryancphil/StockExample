package com.ryanphillips.rpstocks.portfolio.data

import com.ryanphillips.rpstocks.BuildConfig
import com.ryanphillips.rpstocks.portfolio.data.model.PortfolioResponse
import retrofit2.Response
import retrofit2.http.GET

interface PortfolioService {

    @GET(BuildConfig.GET_PORTFOLIO_URL)
    suspend fun getPortfolio(): Response<PortfolioResponse>

    @GET(BuildConfig.GET_MALFORMED_URL)
    suspend fun getMalformedPortfolio(): Response<PortfolioResponse>

    @GET(BuildConfig.GET_EMPTY_URL)
    suspend fun getEmptyPortfolio(): Response<PortfolioResponse>
}