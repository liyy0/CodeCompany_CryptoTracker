package com.example.codecompany_cryptotracker.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.codecompany_cryptotracker.data.model.CoinNameItem

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Typography
import androidx.compose.material3.Divider
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.codecompany_cryptotracker.R

@Composable
fun CryptoInfoCard(coin: CoinNameItem) {
    Column(

    ) {
        CoinInfoRow(stringResource(R.string.price_detailcard), formatNumber(coin.currentPrice))
        CoinInfoRow(stringResource(R.string.market_cap_detailcard), formatNumber(coin.marketCap))
        CoinInfoRow(stringResource(R.string._24h_change), "${coin.priceChangePercentage24h}%")
        CoinInfoRow(stringResource(R.string.volume_24h), formatNumber(coin.totalVolume))
        CoinInfoRow(stringResource(R.string.ath), formatNumber(coin.ath))
        CoinInfoRow(stringResource(R.string.atl), formatNumber(coin.atl))
        CoinInfoRow(title = stringResource(R.string.market_cap_rank), value = "${coin.marketCapRank}")
        CoinInfoRow(title = stringResource(R.string.circulating_supply), value = formatNumber(coin.circulatingSupply))
        Spacer(modifier = Modifier.height(12.dp))
        Divider(modifier = Modifier
            .fillMaxWidth()
            .height(1.dp))
        Spacer(modifier = Modifier.height(12.dp))
    }
}

@Composable
fun CoinInfoRow(title: String, value: String) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        contentColor = Color.Transparent,
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge.copy(color = MaterialTheme.colorScheme.primary),
                modifier = Modifier.weight(1f)
            )
            Text(
                text = value,
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.onSurface
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
@Preview(showBackground = true)
@Composable
fun PreviewCryptoInfoCard() {
    CryptoInfoCard(
        CoinNameItem(
            id = "bitcoin",
            name = "Bitcoin",
            symbol = "BTC",
            image = "",
            currentPrice = 50000.0,
            marketCap = 1000000000000,
            marketCapRank = 1,
            fullyDilutedValuation = null,
            totalVolume = 1000000000.0,
            high24h = 60000.0,
            low24h = 40000.0,
            priceChange24h = 1000.0,
            priceChangePercentage24h = 2.0,
            marketCapChange24h = 1000000000.0,
            marketCapChangePercentage24h = 2.0,
            circulatingSupply = 1000000.0,
            totalSupply = null,
            maxSupply = null,
            ath = 60000.0,
            athChangePercentage = 10.0,
            athDate = "2022-01-01T00:00:00.000Z",
            atl = 10000.0,
            atlChangePercentage = 50.0,
            atlDate = "2022-01-01T00:00:00.000Z",
            roi = null,
            lastUpdated = "2022-01-01T00:00:00.000Z"
        )
    )
}