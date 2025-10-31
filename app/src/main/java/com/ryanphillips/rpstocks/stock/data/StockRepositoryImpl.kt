package com.ryanphillips.rpstocks.stock.data

import android.util.Log
import com.ryanphillips.rpstocks.stock.data.model.PortfolioResponse
import javax.inject.Inject

class StockRepositoryImpl
@Inject constructor(
    private val stockService: StockService
): StockRepository {

    override suspend fun getPortfolio(): PortfolioResponse? {
        return try {
            stockService.getPortfolio()
        } catch (e: Exception) {
            Log.e("StockRepo", e.toString())
            null
        }
    }

    override suspend fun getMalformedPortfolio(): PortfolioResponse? {
        return try {
            stockService.getMalformedPortfolio()
        } catch (e: Exception) {
            Log.e("Error", "Parse Exception: $e")
            null
        }
    }

    override suspend fun getEmptyPortfolio(): PortfolioResponse? {
        return try {
            val empty = stockService.getEmptyPortfolio()
            Log.e("Empty", empty.toString())
            empty
        } catch (e: Exception) {
            null
        }
    }
}