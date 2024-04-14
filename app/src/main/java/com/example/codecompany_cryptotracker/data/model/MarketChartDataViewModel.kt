package com.example.codecompany_cryptotracker.data.model

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.codecompany_cryptotracker.network.CoinRepos
import com.example.codecompany_cryptotracker.network.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MarketChartDataViewModel
    (private val coinRepos: CoinRepos, private val id: String): ViewModel() {
    var initialValue = MarketChartDataModel(
        market_caps = listOf(),
        prices = listOf(),
        total_volumes = listOf()
    )
    private val _products = MutableStateFlow<MarketChartDataModel>(initialValue)

    val products = _products.asStateFlow()

    private val _showErrorToastChannel = Channel<Boolean>()
    val showErrorToastChannel = _showErrorToastChannel.receiveAsFlow()

    init {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                Log.d("ViewModel", "Detail Data Init")
            coinRepos.getMarkectChartData(id, "usd", 90, "daily" ).collectLatest { result ->
                when(result) {
                    is Result.Error -> {
                        Log.d("ViewModel", "Error")
                        _showErrorToastChannel.send(true)
                    }
                    is Result.Success -> {
                        Log.d("ViewModel", "Success")
                        result.data?.let { products ->
                            _products.update { products }
                        }
                        Log.d("ViewModel", products.value.prices.toString())
                    }
                }
            }
        }}
    }
}