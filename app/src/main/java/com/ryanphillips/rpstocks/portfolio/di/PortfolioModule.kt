package com.ryanphillips.rpstocks.portfolio.di

import com.ryanphillips.rpstocks.portfolio.domain.PortfolioRepository
import com.ryanphillips.rpstocks.portfolio.data.PortfolioRepositoryImpl
import com.ryanphillips.rpstocks.portfolio.data.PortfolioService
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class PortfolioModule {
    @Binds
    abstract fun bindPortfolioRepository(portfolioRepositoryImpl: PortfolioRepositoryImpl): PortfolioRepository
}