package com.ryanphillips.rpstocks.portfolio.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ryanphillips.rpstocks.core.domain.DataError
import com.ryanphillips.rpstocks.core.domain.Result
import com.ryanphillips.rpstocks.portfolio.domain.PortfolioDto
import com.ryanphillips.rpstocks.portfolio.domain.PortfolioRepository
import com.ryanphillips.rpstocks.portfolio.presentation.model.PortfolioAction
import com.ryanphillips.rpstocks.portfolio.presentation.model.PortfolioState
import com.ryanphillips.rpstocks.portfolio.presentation.model.toStockState
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
class PortfolioViewModel
@Inject constructor(
    val portfolioRepository: PortfolioRepository
) : ViewModel() {

    // StateFlow
    private val _uiState = MutableStateFlow(PortfolioState())
    val uiState = _uiState
        .onStart {
            loadPortfolio()
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = PortfolioState()
        )

    fun onAction(action: PortfolioAction) {
        when (action) {
            PortfolioAction.OnPortfolioClick -> {
                viewModelScope.launch {
                    _uiState.update {
                        it.copy(
                            isLoading = true
                        )
                    }
                    loadPortfolio()
                }
            }
            PortfolioAction.OnMalformedClick -> {
                viewModelScope.launch {
                    portfolioRepository.getMalformedPortfolio().collect { result ->
                        updateState(result)
                    }
                }
            }
            PortfolioAction.OnEmptyClick -> {
                viewModelScope.launch {
                    portfolioRepository.getEmptyPortfolio().collect { result ->
                        updateState(result)
                    }
                }
            }
        }
    }

    private suspend fun loadPortfolio() {
        delay(3000L) // DEMO ONLY TO ENJOY LOADING ANIMATION
        portfolioRepository.getPortfolio().collect { result -> updateState(result) }
    }

    private fun updateState(result: Result<PortfolioDto, DataError.Network>) {
        when (result) {
            is Result.Error -> {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        isError = true
                    )
                }
            }
            is Result.Success -> {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        isError = false,
                        stocks = result.data.stocks.map { stockDto ->
                            stockDto.toStockState()
                        }
                    )
                }
            }
        }
    }
}