package com.ryanphillips.rpstocks.portfolio.di

import com.ryanphillips.rpstocks.portfolio.data.PortfolioService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PortfolioServiceModule {
    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): PortfolioService {
        return retrofit.create(PortfolioService::class.java)
    }
}