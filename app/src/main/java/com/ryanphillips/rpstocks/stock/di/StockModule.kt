package com.ryanphillips.rpstocks.stock.di

import com.ryanphillips.rpstocks.stock.data.StockRepository
import com.ryanphillips.rpstocks.stock.data.StockRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class StockModule {
    @Binds
    abstract fun bindStockRepository(stockRepositoryImpl: StockRepositoryImpl): StockRepository
}