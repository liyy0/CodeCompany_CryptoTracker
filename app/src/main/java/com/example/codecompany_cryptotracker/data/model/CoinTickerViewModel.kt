package com.example.codecompany_cryptotracker.data.model

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.codecompany_cryptotracker.network.CoinRepos
import com.example.codecompany_cryptotracker.network.Result
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CoinTickerViewModel
        (private val coinRepos: CoinRepos, private val id: String): ViewModel() {
        var initialValue = CoinTickerData(
            name = "",
            tickers = emptyList()
        )

        private val _products = MutableStateFlow<CoinTickerData>(initialValue)
        val products = _products.asStateFlow()

        private val _showErrorToastChannel = Channel<Boolean>()
        val showErrorToastChannel = _showErrorToastChannel.receiveAsFlow()

        init {
            viewModelScope.launch {
                Log.d("ViewModel", "Init CoinTicker View Model")
                coinRepos.getCoinTickerDataById(id).collectLatest { result ->
                    when(result) {
                        is Result.Error -> {
                            result.message?.let { Log.d("ViewModel", it) }
                            _showErrorToastChannel.send(true)
                        }
                        is Result.Success -> {
                            Log.d("ViewModel", "Success Coin Ticker")
                            result.data?.let { products ->
                                _products.update { products }
                            }
                            Log.d("ViewModel", products.value.toString())
                        }
                    }
                }
            }
        }
    }
