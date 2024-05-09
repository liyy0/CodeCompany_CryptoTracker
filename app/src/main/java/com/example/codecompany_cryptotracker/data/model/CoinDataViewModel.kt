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
//ViewModel for CoinData
class CoinDataViewModel
    (private val coinRepos: CoinRepos, private val id: String): ViewModel() {
    var initialValue = CoinData(
        id = "",
    symbol = "",
    name = "",
    webSlug = "",
    assetPlatformId = null,
    platforms = emptyMap(),
    detailPlatforms = emptyMap(),
    blockTimeInMinutes = 0,
    hashingAlgorithm = "",
    categories = emptyList(),
    previewListing = false,
    publicNotice = null,
    additionalNotices = emptyList(),
    description = Description(en=""),
    links = Links(
        homepage= emptyList(),
        whitepaper= "",
        blockchainSite= emptyList(),
        officialForumUrl= emptyList(),
        chatUrl= emptyList(),
        announcementUrl= emptyList(),
        twitterScreenName="",
        facebookUsername="",
        bitcointalkThreadIdentifier= null,
        telegramChannelIdentifier="",
        subredditUrl="",
        reposUrl=null),
    image = null,
    countryOrigin = "",
    genesisDate = "",
    sentimentVotesUpPercentage = 0.0,
    sentimentVotesDownPercentage = 0.0,
    watchlistPortfolioUsers = 0,
    marketCapRank = 0,
    communityData = null,
    developerData = null,
    statusUpdates = emptyList(),
    lastUpdated = ""
    )

    private val _products = MutableStateFlow<CoinData>(initialValue)
    val products = _products.asStateFlow()

    private val _showErrorToastChannel = Channel<Boolean>()
    val showErrorToastChannel = _showErrorToastChannel.receiveAsFlow()

    init {
        viewModelScope.launch {
            Log.d("ViewModel", "Init CoinData View Model")
            coinRepos.getCoinDataById(id).collectLatest { result ->
                when(result) {
                    is Result.Error -> {
                        result.message?.let { Log.d("ViewModel", it) }
                        _showErrorToastChannel.send(true)
                    }
                    is Result.Success -> {
                        Log.d("ViewModel", "Success Coin Data")
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