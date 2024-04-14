package com.example.codecompany_cryptotracker.presentation

import androidx.compose.foundation.clickable
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

import com.example.codecompany_cryptotracker.util.loadAssets

@Composable
fun AssetList(navController: NavController) {
    val context = LocalContext.current
    val assets = remember { loadAssets(context) }
//    var coinNameList = CoinNameViewModel(CoinReposImp(RetrofitInstance.api))
//    var coinNamesList1 = coinNames.products.collectAsState().value

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
              modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clickable { onItemClick() },
        shape = RoundedCornerShape(8.dp), // Adds rounded corners to the Card
        elevation =  CardDefaults.cardElevation(),

    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically // Aligns items vertically in the center
        ) {
            // AsyncImage for loading and displaying an image from a URL
            AsyncImage(
                model = coin.image,
                contentDescription = coin.id?.lowercase(),
                modifier = Modifier
                    .size(50.dp) // Makes the image a fixed size, ensuring uniformity
                    .clip(RoundedCornerShape(50)), // Clips the image to be circular
                contentScale = ContentScale.Crop // Crops the image to fit the modifier bounds
            )
            Spacer(modifier = Modifier.width(16.dp))
            // Text displaying the asset ID
            coin.id?.let {
                Text(
                    text = it.uppercase(),
                    modifier = Modifier.padding(start = 8.dp),
                    color = Color.Black, // Sets the text color
                    style = MaterialTheme.typography.displayLarge // Uses Material Theme typography
                )
            }
        }
    }
}
