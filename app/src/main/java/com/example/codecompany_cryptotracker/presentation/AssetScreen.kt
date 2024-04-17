package com.example.codecompany_cryptotracker.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.codecompany_cryptotracker.data.model.CoinNameItem
import com.example.codecompany_cryptotracker.data.model.CoinNameViewModel
import com.example.codecompany_cryptotracker.network.CoinReposImp
import com.example.codecompany_cryptotracker.network.RetrofitInstance

import com.example.codecompany_cryptotracker.util.loadAssets

@Composable
fun AssetList(navController: NavController) {

    var tempviewModel = remember {
        CoinNameViewModel(CoinReposImp(RetrofitInstance.api))
    }
    var coinNamesList = tempviewModel.products.collectAsState().value

    LazyColumn {
        items(coinNamesList) { asset ->
                AssetItem(asset,
                    onItemClick = {
//                        Log.d("AssetList", "Asset clicked: ${asset.asset_id}")
                        navController.navigate("AssetDetail/${asset.id}")

                    })
            }

        }
    }

@Composable
fun AssetItem(
    coin: CoinNameItem,
    onItemClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clickable { onItemClick() },
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = coin.image,
                contentDescription = coin.name,
                modifier = Modifier
                    .size(50.dp)
                    .clip(RoundedCornerShape(50)),
                contentScale = ContentScale.Crop,
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(
                    text = coin.name,
                    style = MaterialTheme.typography.titleLarge, // Using titleLarge
                    color = Color.Black,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
                Text(
                    text = "Price: $${coin.currentPrice}",
                    style = MaterialTheme.typography.bodyLarge, // Using bodyLarge
                    color = Color.Black.copy(alpha = 0.8f),
                    modifier = Modifier.padding(bottom = 4.dp)
                )
                Text(
                    text = "Market Cap: $${coin.marketCap}",
                    style = MaterialTheme.typography.bodyLarge, // Using bodyLarge
                    color = Color.Black.copy(alpha = 0.8f),
                    modifier = Modifier.padding(bottom = 4.dp)
                )
                Text(
                    text = "Price Change (24h): ${coin.priceChangePercentage24h}%",
                    style = MaterialTheme.typography.bodyLarge, // Using bodyLarge
                    color = if (coin.priceChangePercentage24h >= 0) Color.Green else Color.Red,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
            }
        }
    }
}

