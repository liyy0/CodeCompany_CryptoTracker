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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.codecompany_cryptotracker.data.model.CoinNameItem

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Typography
import androidx.compose.material3.Divider
import androidx.compose.ui.res.stringResource
import com.example.codecompany_cryptotracker.R

@Composable
fun CryptoInfoCard(coin: CoinNameItem) {
    Card(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier.padding(all = 16.dp)
        ) {
            CoinInfoRow(stringResource(R.string.price_detailcard), formatNumber(coin.currentPrice))
            CoinInfoRow(stringResource(R.string.market_cap_detailcard), formatNumber(coin.marketCap))
            CoinInfoRow(stringResource(R.string._24h_change), "${coin.priceChangePercentage24h}%")
            CoinInfoRow(stringResource(R.string.volume_24h), formatNumber(coin.totalVolume))
            CoinInfoRow(stringResource(R.string.ath), formatNumber(coin.ath))
            CoinInfoRow(stringResource(R.string.atl), formatNumber(coin.atl))
            CoinInfoRow(title = stringResource(R.string.market_cap_rank), value = "${coin.marketCapRank}")
            CoinInfoRow(title = stringResource(R.string.circulating_supply), value = formatNumber(coin.circulatingSupply))
        }
    }
}

@Composable
fun CoinInfoRow(title: String, value: String) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        color = MaterialTheme.colorScheme.surfaceVariant,
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
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