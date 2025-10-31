package com.ryanphillips.rpstocks.stock.ui

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
import androidx.compose.foundation.layout.safeDrawing
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
import com.ryanphillips.rpstocks.stock.StockViewModel
import com.ryanphillips.rpstocks.stock.ui.model.PortfolioScreenState

@Composable
fun PortfolioScreen(
    state: PortfolioScreenState,
    updateFetchMode: (StockViewModel.FetchMode) -> Unit
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        contentWindowInsets = WindowInsets.safeDrawing,
        bottomBar = {
            PortfolioBottomBar(updateFetchMode)
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
                                it.amount
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun PortfolioBottomBar(updateFetchMode: (StockViewModel.FetchMode) -> Unit) {
    BottomAppBar {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(
                onClick = {
                    updateFetchMode(StockViewModel.FetchMode.PORTFOLIO)
                }) {
                Text("Portfolio")
            }
            Button(
                onClick = {
                    updateFetchMode(StockViewModel.FetchMode.MALFORMED)
                }) {
                Text("Error")
            }
            Button(
                onClick = {
                    updateFetchMode(StockViewModel.FetchMode.EMPTY)
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
