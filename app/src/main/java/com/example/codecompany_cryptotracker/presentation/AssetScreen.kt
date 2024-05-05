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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.StarBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.codecompany_cryptotracker.R
import com.example.codecompany_cryptotracker.data.model.CoinNameItem
import com.example.codecompany_cryptotracker.data.model.CoinNameViewModel
import com.example.codecompany_cryptotracker.data.model.WatchListData
import com.example.codecompany_cryptotracker.network.CoinReposImp
import com.example.codecompany_cryptotracker.network.RetrofitInstance

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AssetList(navController: NavController,
              watchList: WatchListData) {

    var tempviewModel = remember {
        CoinNameViewModel(CoinReposImp(RetrofitInstance.api), null)
    }
    var coinNamesList = tempviewModel.products.collectAsState().value
    // State variable to hold the current search query
    var searchQuery by remember { mutableStateOf("") }

    Column {
        TopAppBar(
            title = {
                Text(
                    text = stringResource(R.string.crypto_tracker),
                    textAlign = TextAlign.Center, // Aligning text to the center
                    modifier = Modifier.fillMaxWidth() // Making sure the text takes full width
                )
            }
        )

        // TextField for user input
        TextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            label = { Text("Search") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )

        LazyColumn {
            val filteredList = coinNamesList.filter { asset ->
                asset.name.contains(searchQuery, ignoreCase = true)
            }

            items(filteredList) { asset ->
                AssetItem(asset,
                    onItemClick = {
                        navController.navigate("AssetDetail/${asset.id}")
                    },
                    watchList
                )
            }
        }
    }
    }

@Composable
fun AssetItem(
    coin: CoinNameItem,
    onItemClick: () -> Unit,
    watchList: WatchListData,
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
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = coin.name,
                    style = MaterialTheme.typography.titleLarge, // Using titleLarge
                    color = Color.Black,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
                Text(
                    text = stringResource(R.string.price, coin.currentPrice),
                    style = MaterialTheme.typography.bodyLarge, // Using bodyLarge
                    color = Color.Black.copy(alpha = 0.8f),
                    modifier = Modifier.padding(bottom = 4.dp)
                )
                Text(
                    text = stringResource(R.string.price_change_24h, coin.priceChangePercentage24h),
                    style = MaterialTheme.typography.bodyLarge, // Using bodyLarge
                    color = if (coin.priceChangePercentage24h >= 0) Color.Green else Color.Red,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
            }
            FavouriteIconButton(watchList = watchList, coin = coin )
        }
    }
}
@Composable
fun FavouriteIconButton(watchList: WatchListData, coin:CoinNameItem){
    var isStarred by remember { mutableStateOf(false)}
    isStarred = watchList.isCoinId(coin.id)
    IconButton(
        onClick = {
            // Toggle the star status
            if (isStarred) {
                watchList.removeCoinId(coin.id)
                isStarred = false;
            } else {
                watchList.addCoinId(coin.id)
                isStarred = true;
            }
        },
        modifier = Modifier.size(48.dp)
    ) {
        Icon(
            imageVector = if (isStarred) Icons.Filled.Star else Icons.Outlined.StarBorder,
            contentDescription = "Star",
            tint = if (isStarred) MaterialTheme.colorScheme.primary else LocalContentColor.current
        )
    }
}
