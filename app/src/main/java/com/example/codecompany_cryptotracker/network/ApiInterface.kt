package com.example.codecompany_cryptotracker.network

import com.example.codecompany_cryptotracker.data.model.CoinName
import com.example.codecompany_cryptotracker.data.model.CoinNameItem
import com.example.codecompany_cryptotracker.data.model.MarketChartDataModel
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiInterface {

    //List all coin with id name and symbol
    @GET("coins/list")
    suspend fun getAllCoinName(
        @Query("x_cg_demo_api_key")apiKey:String
    ): CoinName

    @GET("coins/{id}/market_chart")
    suspend fun getMarketChart(
        @Path("id") id: String,
        @Query("vs_currency") currency: String,
        @Query("days") days: Int, // Optionally, you can specify the number of days for data
        @Query("interval") interval: String, // Optionally, you can specify the interval for data
        @Query("x_cg_demo_api_key")apiKey:String
    ): MarketChartDataModel



    companion object{
        const val BASE_URL = "https://api.coingecko.com/api/v3/"
    }

}