package com.example.codecompany_cryptotracker.presentation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.codecompany_cryptotracker.data.model.CoinNewsViewModel
import com.example.codecompany_cryptotracker.network.CoinReposImp

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NewsList() {

    var newsViewModel = remember{
            CoinNewsViewModel(CoinReposImp(RetrofitNewsInstance.api), "cryptocurrency")
    }
    var coinNews = newsViewModel.products.collectAsState().value.articles


    Box(modifier = Modifier.fillMaxSize()){
        Column(modifier = Modifier
            .fillMaxSize()
            .align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
            ) {
            Text(coinNews.toString())
        }
    }
}
