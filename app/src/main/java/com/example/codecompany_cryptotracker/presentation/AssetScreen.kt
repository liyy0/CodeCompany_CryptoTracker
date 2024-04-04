package com.example.codecompany_cryptotracker.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.toLowerCase
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.compose.rememberImagePainter
import com.example.codecompany_cryptotracker.domain.Asset
import com.example.codecompany_cryptotracker.ui.theme.CodeCompany_CryptoTrackerTheme

@Composable
fun AssetList(assets: List<Asset>) {
    val sortedAssets = assets.sortedByDescending { it.volume_1day_usd ?: 0.0 }
    LazyColumn {
        items(sortedAssets) { asset ->
            if (asset.type_is_crypto == 1){
                AssetItem(asset)
            }

        }
    }
}

@Composable
fun AssetItem(asset: Asset,
              modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .padding(8.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(8.dp), // Adds rounded corners to the Card
        elevation =  CardDefaults.cardElevation()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically // Aligns items vertically in the center
        ) {
            // AsyncImage for loading and displaying an image from a URL
            AsyncImage(
                model = asset.icon_url,
                contentDescription = asset.asset_id?.lowercase(),
                modifier = Modifier
                    .size(50.dp) // Makes the image a fixed size, ensuring uniformity
                    .clip(RoundedCornerShape(50)), // Clips the image to be circular
                contentScale = ContentScale.Crop // Crops the image to fit the modifier bounds
            )
            Spacer(modifier = Modifier.width(16.dp))
            // Text displaying the asset ID
            asset.asset_id?.let {
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

//@Preview(showBackground = true)
//@Composable
//fun Preview() {
//   AssetItem(asset = )
//}

