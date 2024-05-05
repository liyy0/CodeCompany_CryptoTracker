package com.example.codecompany_cryptotracker.presentation
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.positionChange
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.codecompany_cryptotracker.R
import com.example.codecompany_cryptotracker.data.model.CoinNameItem
import com.example.codecompany_cryptotracker.data.model.CoinNameViewModel
import com.example.codecompany_cryptotracker.data.model.WatchListData
import com.example.codecompany_cryptotracker.network.CoinReposImp
import com.example.codecompany_cryptotracker.network.RetrofitInstance
import kotlinx.coroutines.delay
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Favorite(navController: NavController, watchList: WatchListData) {
    if(watchList.getCoinIds().isEmpty()){
        Text(text = "No Coin In WatchList! Pls go to Coin Market Add some!")
    }
    else{
        val marketViewModel = remember {
            CoinNameViewModel(CoinReposImp(RetrofitInstance.api), watchList.getCoinIdsAsString())
        }

        var coinMarketData1 by remember {
            mutableStateOf<List<CoinNameItem>>(emptyList())
        }

        LaunchedEffect(key1 = marketViewModel.products) {
            marketViewModel.products.collect { products ->
                coinMarketData1 = products
            }
        }
        var key by remember {
            mutableStateOf(Int)
        }

        Column {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.crypto_tracker_watchlist),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            )
            LazyColumn() {
                items(coinMarketData1) { coin ->
                    key(coin.id) {
                        SwipeCard(onSwipeLeftOrRight = {
                            watchList.removeCoinId(coin.id)
                            coinMarketData1 = coinMarketData1.filter { it.id != coin.id }
                        }) {
                            watchCoinInfo(
                                coin = coin,
                                onItemClick = { navController.navigate("AssetDetail/${coin.id}") },
                                watchList = watchList
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun watchCoinInfo(coin: CoinNameItem,
                  modifier: Modifier = Modifier,
                  onItemClick: ()-> Unit,
                  watchList: WatchListData,
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
            CoinItem(coin)
            FavouriteIconButton(watchList = watchList, coin = coin )
        }
    }
}

@Composable
fun CoinItem(coin: CoinNameItem) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp
        )
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Text(
                text = coin.name,
                style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Current Price: $${formatNumber(coin.currentPrice)}",
                style = TextStyle(fontSize = 14.sp)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Market Cap: $${formatNumber(coin.marketCap)}",
                style = TextStyle(fontSize = 14.sp)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Row {
                Text(
                    text = "24h High: $${formatNumber(coin.high24h)}",
                    style = TextStyle(fontSize = 14.sp, color = Color.Red)
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = "Low: $${formatNumber(coin.low24h)}",
                    style = TextStyle(fontSize = 14.sp, color = Color(0xFF008000))
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            val priceChangeColor = when {
                coin.priceChange24h > 0 -> Color.Red
                coin.priceChange24h < 0 -> Color(0xFF008000)
                else -> Color.Black
            }
            Text(
                text = "Price Change 24h: ${formatNumber(coin.priceChange24h)} (${formatNumber(coin.priceChangePercentage24h)}%)",
                style = TextStyle(fontSize = 14.sp, color = priceChangeColor)
            )
            // Add more information as needed
        }
    }
}
@Composable
fun SwipeCard(
    onSwipeLeftOrRight: () -> Unit = {},
    swipeThreshold: Float = 400f,
    sensitivityFactor: Float = 3f,
    content: @Composable () -> Unit
) {
    var offset by remember { mutableStateOf(0f) }
    val density = LocalDensity.current.density

    Box(modifier = Modifier
        .offset { IntOffset(offset.roundToInt(), 0) }
        .pointerInput(Unit) {
            detectHorizontalDragGestures(onDragEnd = {
                if (offset > swipeThreshold || offset < -swipeThreshold) {
                    onSwipeLeftOrRight.invoke()
                }
                offset = 0f
            }) { change, dragAmount ->
                offset += (dragAmount / density) * sensitivityFactor
                if (change.positionChange() != Offset.Zero) change.consume()
            }
        }) {
        content()
    }
}



