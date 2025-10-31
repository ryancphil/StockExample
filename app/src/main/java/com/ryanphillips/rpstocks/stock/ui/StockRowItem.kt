package com.ryanphillips.rpstocks.stock.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.ryanphillips.rpstocks.ui.theme.RPStocksTheme
import com.ryanphillips.rpstocks.ui.theme.spacing

@Composable
fun StockRowItem(
    symbol: String,
    name: String,
    price: String,
    amount: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                all = MaterialTheme.spacing.medium
            ),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier.weight(.75f)
        ) {
            Text(
                text = symbol,
                style = MaterialTheme.typography.headlineLarge
            )
            Text(
                text = name,
                color = MaterialTheme.colorScheme.secondary,
                style = MaterialTheme.typography.bodyLarge,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
        Column(
            horizontalAlignment = Alignment.End
        ) {
            Text(
                text = price,
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.headlineLarge
            )
            Text(
                text = "QTY: $amount",
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

@Preview
@Composable
fun StockRowItemPreview() {
    RPStocksTheme {
        StockRowItem(
            symbol = "ZZZ",
            name = "Really long name for stock that goes all the way to the end",
            price = "$1000000.00",
            amount = "5"
        )
    }
}