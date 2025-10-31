package com.ryanphillips.rpstocks.stock

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ryanphillips.rpstocks.stock.data.StockRepository
import com.ryanphillips.rpstocks.stock.data.model.StockResponse
import com.ryanphillips.rpstocks.stock.ui.model.PortfolioScreenState
import com.ryanphillips.rpstocks.stock.ui.model.StockState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StockViewModel
@Inject constructor(
    val stockRepository: StockRepository
) : ViewModel() {

    enum class FetchMode {
        PORTFOLIO,
        MALFORMED,
        EMPTY
    }

    private val _uiState = MutableStateFlow(PortfolioScreenState())
    val uiState = _uiState
        .onStart {
            fetchPortfolio(FetchMode.PORTFOLIO)
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            PortfolioScreenState()
        )

    fun fetchPortfolio(fetchMode: FetchMode) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(isLoading = true)
            }

            // DEMO purpose only: Manual delay to enjoy the loading animation
            delay(3000)

            val portfolioResponse = when (fetchMode) {
                FetchMode.PORTFOLIO -> stockRepository.getPortfolio()
                FetchMode.MALFORMED -> stockRepository.getMalformedPortfolio()
                FetchMode.EMPTY -> stockRepository.getEmptyPortfolio()
            }

            _uiState.update {
                it.copy(
                    isLoading = false,
                    isError = portfolioResponse == null,
                    stocks = mapStockResponseToStockState(portfolioResponse?.stocks)
                )
            }
        }
    }

    fun mapStockResponseToStockState(stocks: List<StockResponse>?): List<StockState> {
        return stocks?.map {
            StockState(
                symbol = it.ticker,
                name = it.name,
                currency = it.currency,
                price = formatPrice(it.currentPriceCents),
                amount = it.quantity?.toString() ?: "-"
            )
        } ?: emptyList()
    }

    fun updateFetchMode(fetchMode: FetchMode) {
        fetchPortfolio(fetchMode)
    }

    @SuppressLint("DefaultLocale")
    fun formatPrice(cents: Int): String {
        val dollars = cents / 100.0
        val formatted = String.format("%.2f", dollars)
        return "$$formatted"
    }


}