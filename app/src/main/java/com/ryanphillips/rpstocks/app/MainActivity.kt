package com.ryanphillips.rpstocks.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ryanphillips.rpstocks.stock.StockViewModel
import com.ryanphillips.rpstocks.stock.ui.PortfolioScreen
import com.ryanphillips.rpstocks.ui.theme.RPStocksTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RPStocksTheme {
                val viewModel: StockViewModel = hiltViewModel()
                val state by viewModel.uiState.collectAsStateWithLifecycle()
                PortfolioScreen(
                    state,
                    updateFetchMode = viewModel::updateFetchMode
                )
            }
        }
    }
}