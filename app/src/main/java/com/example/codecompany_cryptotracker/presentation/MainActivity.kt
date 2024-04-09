package com.example.codecompany_cryptotracker.presentation

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.example.codecompany_cryptotracker.data.model.CoinNameViewModel
import com.example.codecompany_cryptotracker.data.model.MarketChartDataViewModel
import com.example.codecompany_cryptotracker.network.ApiInterface
import com.example.codecompany_cryptotracker.network.CoinRepos
import com.example.codecompany_cryptotracker.network.CoinReposImp
import com.example.codecompany_cryptotracker.ui.theme.CodeCompany_CryptoTrackerTheme
import com.example.codecompany_cryptotracker.util.loadAssets

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var coinNameList = CoinNameViewModel(CoinReposImp(RetrofitInstance.api))
        Log.d("debug - List", coinNameList.products.value.size.toString())
        var coinNamePrice = MarketChartDataViewModel(CoinReposImp(RetrofitInstance.api), "bitcoin")
        Log.d("debug - Price", coinNamePrice.products.value.prices.size.toString())
        setContent {
            val assets = loadAssets(context = applicationContext)
            AssetList(assets = assets)
        }


    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

//@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CodeCompany_CryptoTrackerTheme {
        Greeting("Android")
    }
}

