package com.example.codecompany_cryptotracker.data.model

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
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
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Date

@RequiresApi(Build.VERSION_CODES.O)
class CoinNewsViewModel
    (private val coinRepos: CoinRepos,
     private val id: String,
     private val language: String = "en"): ViewModel() {
    var initialValue = CoinNews(
        status = "init",
        totalResults = -1,
        articles = listOf()
    )
    private val _products = MutableStateFlow<CoinNews>(initialValue)

    val products = _products.asStateFlow()

    private val _showErrorToastChannel = Channel<Boolean>()
    val showErrorToastChannel = _showErrorToastChannel.receiveAsFlow()
    @RequiresApi(Build.VERSION_CODES.O)
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

    @RequiresApi(Build.VERSION_CODES.O)
    val today = LocalDate.now()
    @RequiresApi(Build.VERSION_CODES.O)
    val tenDaysAgo = today.minusDays(10)

    @RequiresApi(Build.VERSION_CODES.O)
    val todayString = today.format(formatter)
    @RequiresApi(Build.VERSION_CODES.O)
    val tenDaysAgoString = tenDaysAgo.format(formatter)

    init {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                Log.d("ViewModel", "News Data Init")
                coinRepos.getCoinNews(id, tenDaysAgoString, todayString, language).collectLatest {result ->
                    when(result) {
                        is Result.Error -> {
                            Log.d("ViewModel", "Error")
                            _showErrorToastChannel.send(true)
                        }
                        is Result.Success -> {
                            Log.d("ViewModel", "Success Coin News")
                            result.data?.let { products ->
                                _products.update { products }
                            }
                            Log.d("ViewModel", products.value.articles.toString())
                        }
                    }
                }
            }
        }
    }
}