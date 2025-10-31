package com.ryanphillips.rpstocks.stock

import com.ryanphillips.rpstocks.stock.data.FakeStockRepository
import com.ryanphillips.rpstocks.stock.data.StockRepository
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class StockViewModelTest {

    private lateinit var viewModel: StockViewModel
    private lateinit var fakeStockRepository: StockRepository

    @Before
    fun setUp() {
        fakeStockRepository = FakeStockRepository()
        viewModel = StockViewModel(fakeStockRepository)
    }

    @Test
    fun `isLoading is true by default`() {
        val state = viewModel.uiState.value

        assertTrue(state.isLoading)
    }

    @Test
    fun `mapStockResponseToStockState returns list when input is valid`() = runBlocking {
        val portfolioResponse = fakeStockRepository.getPortfolio()
        val state = viewModel.mapStockResponseToStockState(stocks = portfolioResponse?.stocks)

        assertTrue(state.isNotEmpty())
    }

    @Test
    fun `mapStockResponseToStockState returns empty list when input is null`() {
        val state = viewModel.mapStockResponseToStockState(stocks = null)

        assertTrue(state.isEmpty())
    }

    @Test
    fun `formatPrice rounds to 2 decimal places`() {
        val cents = 100000
        val dollarsStr = viewModel.formatPrice(cents)
        val has2DecimalPlaces = dollarsStr.split(".").last().length == 2

        assertTrue(has2DecimalPlaces)
    }
}