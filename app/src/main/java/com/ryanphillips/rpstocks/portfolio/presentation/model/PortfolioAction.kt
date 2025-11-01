package com.ryanphillips.rpstocks.portfolio.presentation.model

sealed interface PortfolioAction {
    data object OnPortfolioClick: PortfolioAction
    data object OnMalformedClick: PortfolioAction
    data object OnEmptyClick: PortfolioAction
}