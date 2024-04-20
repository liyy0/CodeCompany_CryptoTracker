package com.example.codecompany_cryptotracker.network

import com.example.codecompany_cryptotracker.data.model.CoinData
import com.example.codecompany_cryptotracker.data.model.CoinName
import com.example.codecompany_cryptotracker.data.model.CoinNameItem
import com.example.codecompany_cryptotracker.data.model.CoinNews
import com.example.codecompany_cryptotracker.data.model.CoinTickerData
import com.example.codecompany_cryptotracker.data.model.MarketChartDataModel
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiInterface {

    //List all coin with id name and symbol
    @GET("coins/markets")
    suspend fun getAllCoinName(
        @Query("x_cg_demo_api_key")apiKey:String,
        @Query("vs_currency") currency: String,
        @Query("locale") local: String
    ): CoinName

    @GET("coins/{id}")
    suspend fun getCoinDataById(
        @Path("id") id: String,
        @Query("x_cg_demo_api_key")apiKey:String,
        @Query("localization") localization: Boolean = false,
        @Query("tickers") tickers: Boolean = false,
        @Query("market_data") marketData: Boolean = false,
        @Query("community_data") communityData: Boolean = true,
        @Query("developer_data") developerData: Boolean = true,
        @Query("sparkline") sparkline: Boolean = false
    ):CoinData

    @GET("coins/{id}/tickers")
    suspend fun getTickersById(
        @Path("id") id: String,
        @Query("x_cg_demo_api_key")apiKey:String,

        @Query("exchange_ids") exchangeIds: String? = "binance",
        @Query("include_exchange_logo") includeExchangeLogo: Boolean = false,
        @Query("page") page: Int? = 1,
        @Query("order") order: String? = "trust_score_desc",
        @Query("depth") depth: Boolean = false
    ): CoinTickerData

    @GET("coins/{id}/market_chart")
    suspend fun getMarketChart(
        @Path("id") id: String,
        @Query("vs_currency") currency: String,
        @Query("days") days: Int, // Optionally, you can specify the number of days for data
        @Query("interval") interval: String, // Optionally, you can specify the interval for data
        @Query("x_cg_demo_api_key")apiKey:String
    ): MarketChartDataModel

    @GET("v2/everything")
    suspend fun getCoinNews(
        @Query("apiKey")apiKey:String,
        @Query("q") coinName: String,
        @Query("from") from: String,
        @Query("to") to: String,
        @Query("language") language: String = "en",
        @Query("pageSize") pageSize: Int = 20,
        @Query("page") page: Int = 1,
        @Query("sortBy") sortBy: String = "relevancy",
    ): CoinNews
//    https://newsapi.org/v2/everything?q=bitcoin&apiKey=e567bf85a2b94579b445aeda638bdeb5&from=2024-04-03&to=2024-04-13&sortBy=popularity&language=en&pageSize=10&page=3



    companion object{
        const val BASE_URL = "https://api.coingecko.com/api/v3/"
        const val BASE_URL_NEWS = "https://newsapi.org/"
    }

}