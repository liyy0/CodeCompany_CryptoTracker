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

class CoinNameViewModel (
        private val coinRepos: CoinRepos,
    private val id: String?,
    private val locale: String = "en",
    private val vs_currency: String = "usd"
): ViewModel() {

        private val _products = MutableStateFlow<List<CoinNameItem>>(emptyList())
        val products = _products.asStateFlow()

        private val _showErrorToastChannel = Channel<Boolean>()
        val showErrorToastChannel = _showErrorToastChannel.receiveAsFlow()

        fun changeLocal(currency:String, locale: String){
            viewModelScope.launch {
                coinRepos.getAllCoinList(currency, locale, id).collectLatest { result ->
                    when(result) {
                        is Result.Error -> {
                            Log.d("ViewModel", "Error")
                            _showErrorToastChannel.send(true)
                        }
                        is Result.Success -> {
                            Log.d("ViewModel", "Success Coin Name List")
                            result.data?.let { products ->
                                _products.update { products }
                            }
                            Log.d("ViewModel", products.value.toString())
                        }
                    }
                }
            }
        }

        init {
            viewModelScope.launch {
                    Log.d("ViewModel", "Init Coin Name View Model")
                coinRepos.getAllCoinList(vs_currency, locale, id).collectLatest { result ->
                    when(result) {
                        is Result.Error -> {
                            result.message?.let { Log.d("ViewModel", it) }
                            _showErrorToastChannel.send(true)
                        }
                        is Result.Success -> {
                            Log.d("ViewModel", "Success")
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