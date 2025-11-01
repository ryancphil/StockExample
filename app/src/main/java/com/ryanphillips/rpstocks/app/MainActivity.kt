package com.ryanphillips.rpstocks.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.ryanphillips.rpstocks.core.designsystem.theme.RPStocksTheme
import com.ryanphillips.rpstocks.portfolio.presentation.PortfolioScreenRoot
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RPStocksTheme {
                PortfolioScreenRoot()
            }
        }
    }
}