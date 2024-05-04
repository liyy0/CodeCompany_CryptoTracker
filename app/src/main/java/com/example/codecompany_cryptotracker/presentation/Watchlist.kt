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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.codecompany_cryptotracker.R
import com.example.codecompany_cryptotracker.data.model.CoinNameItem
import com.example.codecompany_cryptotracker.data.model.CoinNameViewModel
import com.example.codecompany_cryptotracker.network.CoinReposImp
import com.example.codecompany_cryptotracker.network.RetrofitInstance
import com.example.codecompany_cryptotracker.network.RetrofitNewsInstance

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Favorite(navController: NavController) {
    var marketViewModel = remember{
        CoinNameViewModel(CoinReposImp(RetrofitInstance.api), "bitcoin")
    }
    var coinMarketData = marketViewModel.products.collectAsState().value
//    var data = remember{coinMarketData}
    TopAppBar(
        title = {
            Text(
                text = stringResource(R.string.watch_list),
//                text = coinMarketData.toString(),

                textAlign = TextAlign.Center, // Aligning text to the center
                modifier = Modifier.fillMaxWidth() // Making sure the text takes full width
            )
        }
    )

    Card {
        Text(text = coinMarketData.toString())
    }
}

@Preview
@Composable
fun PreviewFavorite() {

    Favorite(rememberNavController())
}
