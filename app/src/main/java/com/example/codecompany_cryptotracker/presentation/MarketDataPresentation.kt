package com.example.codecompany_cryptotracker.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.codecompany_cryptotracker.data.model.CoinNameItem

@Composable
fun CryptoInfoCard(coin: CoinNameItem) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp
        ),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                CoinInfoRow("Price:", formatNumber(coin.currentPrice))
                CoinInfoRow("Market Cap:", formatNumber(coin.marketCap))
                CoinInfoRow("24h Change:", "${coin.priceChangePercentage24h}%")
                CoinInfoRow("Volume (24h):", formatNumber(coin.totalVolume))
                CoinInfoRow("ATH:", formatNumber(coin.ath))
                CoinInfoRow("ATL:", formatNumber(coin.atl))
                CoinInfoRow("Market Cap Rank:", "${coin.marketCapRank}")
                CoinInfoRow("Circulating Supply:", formatNumber(coin.circulatingSupply))
            }
        }
    }
}


@Composable
fun CoinInfoRow(title: String, value: String) {
    Card(
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        ),
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = title,
                style = TextStyle(
                    fontSize = 16.sp,
                    color = Color.Black
                ),
                modifier = Modifier.padding(8.dp)
            )
            Text(
                text = value,
                style = TextStyle(
                    fontSize = 16.sp,
                    color = Color.Black
                ),
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}
fun formatNumber(number: Number): String {
    val formattedNumber = when (number) {
        is Double -> {
            when {
                number >= 1_000_000_000 -> "${String.format("%.2f", number / 1_000_000_000)}B"
                number >= 1_000_000 -> "${String.format("%.2f", number / 1_000_000)}M"
                number >= 1_000 -> "${String.format("%.2f", number / 1_000)}K"
                else -> String.format("%.2f", number)
            }
        }
        is Float -> {
            when {
                number >= 1_000_000_000 -> "${String.format("%.2f", number / 1_000_000_000)}B"
                number >= 1_000_000 -> "${String.format("%.2f", number / 1_000_000)}M"
                number >= 1_000 -> "${String.format("%.2f", number / 1_000)}K"
                else -> String.format("%.2f", number)
            }
        }
        else -> String.format("%,.0f", number)
    }

    // Check if the formatted number is "0.00" and adjust to keep two significant figures
    return if (formattedNumber == "0.00") {
        number.toString()
    } else {
        formattedNumber
    }
}


fun formatNumber(number: Long): String {
    return when {
        number >= 1_000_000_000 -> "${String.format("%.2f", number.toDouble() / 1_000_000_000)}B"
        number >= 1_000_000 -> "${String.format("%.2f", number.toDouble() / 1_000_000)}M"
        number >= 1_000 -> "${String.format("%.2f", number.toDouble() / 1_000)}K"
        else -> "$number"
    }
}