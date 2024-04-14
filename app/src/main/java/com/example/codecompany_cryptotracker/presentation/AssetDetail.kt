package com.example.codecompany_cryptotracker.presentation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import com.example.codecompany_cryptotracker.data.model.CoinNameViewModel
import com.example.codecompany_cryptotracker.data.model.MarketChartDataViewModel
import com.example.codecompany_cryptotracker.network.CoinReposImp


@Composable
fun AssetDetail(assetId: String?) {

    var viewModel = remember{
        if (assetId != null) {
            MarketChartDataViewModel(CoinReposImp(RetrofitInstance.api), assetId)
        }
        else{MarketChartDataViewModel(CoinReposImp(RetrofitInstance.api), "BitCoin")}
    }
    var coinPrice = viewModel.products.collectAsState().value

    Text(text = "Asset Detail: $assetId")
    Text(text = coinPrice.toString())

}