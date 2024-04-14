package com.example.codecompany_cryptotracker.presentation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import com.example.codecompany_cryptotracker.data.model.CoinNameViewModel
import com.example.codecompany_cryptotracker.data.model.CoinNewsViewModel
import com.example.codecompany_cryptotracker.data.model.MarketChartDataViewModel
import com.example.codecompany_cryptotracker.network.CoinReposImp


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AssetDetail(assetId: String?) {

    var PriceviewModel = remember{
        if (assetId != null) {
            MarketChartDataViewModel(CoinReposImp(RetrofitInstance.api), assetId)
        }
        else{MarketChartDataViewModel(CoinReposImp(RetrofitInstance.api), "BitCoin")}
    }

    var newsViewModel = remember{
        if (assetId != null) {
            CoinNewsViewModel(CoinReposImp(RetrofitNewsInstance.api), assetId)
        }
        else{CoinNewsViewModel(CoinReposImp(RetrofitNewsInstance.api), "BitCoin")}
    }
    var coinPrice = PriceviewModel.products.collectAsState().value
    var coinNews = newsViewModel.products.collectAsState().value.articles

    Text(text = "Asset Detail: $assetId")
    Text(text = coinNews.toString())

}