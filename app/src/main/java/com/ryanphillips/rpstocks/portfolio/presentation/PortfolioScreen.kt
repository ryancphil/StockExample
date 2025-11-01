package com.ryanphillips.rpstocks.portfolio.presentation

import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContent
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ryanphillips.rpstocks.core.presentation.designsystem.theme.RPStocksTheme
import com.ryanphillips.rpstocks.portfolio.presentation.component.StockRowItem
import com.ryanphillips.rpstocks.portfolio.presentation.model.PortfolioAction
import com.ryanphillips.rpstocks.portfolio.presentation.model.PortfolioState

@Composable
fun PortfolioScreenRoot(
    viewModel: PortfolioViewModel = hiltViewModel(),
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    PortfolioScreen(
        state = state,
        onAction = viewModel::onAction
    )
}

@Composable
fun PortfolioScreen(
    state: PortfolioState,
    onAction: (PortfolioAction) -> Unit,
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        contentWindowInsets = WindowInsets.safeDrawing,
        bottomBar = {
            PortfolioBottomBar(
                onAction = onAction
            )
        }) { innerPadding ->
        Box(
            modifier = Modifier.padding(innerPadding)
        ) {
            if (state.isLoading) {
                StatusBox { LoadingStatus() }
            } else if (state.isError) {
                StatusBox { ErrorStatus() }
            } else {
                if (state.stocks.isEmpty()) {
                    StatusBox { EmptyStatus() }
                } else {
                    LazyColumn {
                        items(state.stocks) {
                            StockRowItem(
                                it.symbol,
                                it.name,
                                it.price,
                                it.amount,
                                it.timestamp
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun PortfolioBottomBar(
    onAction: (PortfolioAction) -> Unit
) {
    BottomAppBar {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(
                onClick = {
                    onAction(PortfolioAction.OnPortfolioClick)
                }) {
                Text("Portfolio")
            }
            Button(
                onClick = {
                    onAction(PortfolioAction.OnMalformedClick)
                }) {
                Text("Error")
            }
            Button(
                onClick = {
                    onAction(PortfolioAction.OnEmptyClick)
                }) {
                Text("Empty")
            }
        }
    }
}

@Composable
fun StatusBox(
    content: @Composable () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            content()
        }
    }
}


@Composable
fun EmptyStatus() {
    Text(
        text = "📂",
        fontSize = 100.sp
    )
    Text(
        text = "Portfolio is empty.",
        fontSize = 15.sp
    )
}

@Composable
fun ErrorStatus() {
    Text(
        text = "😞",
        fontSize = 100.sp
    )
    Text(
        text = "An error occurred.",
        fontSize = 15.sp
    )
}

@Composable
fun LoadingStatus() {
    val infiniteTransition = rememberInfiniteTransition(label = "infinite transition")
    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000)
        ),
        label = "rotation animation"
    )
    Text(
        modifier = Modifier.graphicsLayer {
            rotationZ = rotation
        },
        text = "🤑",
        fontSize = 100.sp
    )
    Text(
        text = "Fetching portfolio...",
        fontSize = 15.sp
    )
}

@Preview
@Composable
private fun PortfolioScreenPreview() {
    RPStocksTheme {
        PortfolioScreen(
            state = PortfolioState(),
            onAction = {}
        )
    }
}

@Preview
@Composable
fun LoadingStatusPreview() {
    StatusBox {
        LoadingStatus()
    }
}

@Preview
@Composable
fun ErrorStatusPreview() {
    StatusBox {
        ErrorStatus()
    }
}

@Preview
@Composable
fun EmptyStatusPreview() {
    StatusBox {
        EmptyStatus()
    }
}
