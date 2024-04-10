package com.example.codecompany_cryptotracker.data.remote

import com.example.codecompany_cryptotracker.domain.Asset
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface AssetDetailApi {
    @GET("assets/icons/{size}")
    suspend fun getMeta(
        @Header("Accept") accept: String,
        @Header("X-CoinAPI-Key") apiKey: String,
        @Query("size") size: String
    ): List<Asset>

    companion object {
        const val BASE_URL = "https://rest.coinapi.io/v1/"
        const val API_KEY = "9AEEB653-DF05-4780-A190-5518B3276992"
    }
}
