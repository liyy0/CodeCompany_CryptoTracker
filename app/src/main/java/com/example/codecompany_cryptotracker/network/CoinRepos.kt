package com.example.codecompany_cryptotracker.network

import com.example.codecompany_cryptotracker.data.model.CoinName
import com.example.codecompany_cryptotracker.data.model.CoinNameItem
import com.example.codecompany_cryptotracker.data.model.MarketChartDataModel
import kotlinx.coroutines.flow.Flow
import retrofit2.http.Query

interface CoinRepos {
        suspend fun getAllCoinList(curreny: String, locale: String): Flow<Result<List<CoinNameItem>>>

        suspend fun getMarkectChartData(id: String,currency: String, days: Int, interval: String):Flow<Result<MarketChartDataModel>>
    }