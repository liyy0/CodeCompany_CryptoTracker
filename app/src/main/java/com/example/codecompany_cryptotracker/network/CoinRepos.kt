package com.example.codecompany_cryptotracker.network

import com.example.codecompany_cryptotracker.data.model.CoinData
import com.example.codecompany_cryptotracker.data.model.CoinName
import com.example.codecompany_cryptotracker.data.model.CoinNameItem
import com.example.codecompany_cryptotracker.data.model.CoinNews
import com.example.codecompany_cryptotracker.data.model.CoinTickerData
import com.example.codecompany_cryptotracker.data.model.MarketChartDataModel
import kotlinx.coroutines.flow.Flow
import retrofit2.http.Path
import retrofit2.http.Query

interface CoinRepos {
        suspend fun getAllCoinList(curreny: String, locale: String): Flow<Result<List<CoinNameItem>>>

    suspend fun getCoinDataById(id: String,
                                communityData: Boolean = true,
                                developerData: Boolean = true): Flow<Result<CoinData>>


    suspend fun getCoinTickerDataById(id: String,
                                      exchangeIds: String? = "binance",
                                      includeExchangeLogo: Boolean = false,
                                      page: Int? = 1,
                                      order: String? = "trust_score_desc",
                                      depth: Boolean = false): Flow<Result<CoinTickerData>>


        suspend fun getMarkectChartData(id: String,currency: String, days: Int, interval: String):Flow<Result<MarketChartDataModel>>

        suspend fun getCoinNews(coinName: String, from: String, to: String, language: String = "en", pageSize: Int = 10, page: Int = 1, sortBy: String = "popularity", ):Flow<Result<CoinNews>>

    }