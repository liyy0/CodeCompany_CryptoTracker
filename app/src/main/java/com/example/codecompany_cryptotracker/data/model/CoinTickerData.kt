package com.example.codecompany_cryptotracker.data.model

import com.google.gson.annotations.SerializedName

data class Market(
    val name: String,
    val identifier: String,
    @SerializedName("has_trading_incentive") val hasTradingIncentive: Boolean
)

data class ConvertedLast(
    val btc: Double,
    val eth: Double,
    val usd: Int
)

data class ConvertedVolume(
    val btc: Double,
    val eth: Double,
    val usd: Long
)

data class Ticker(
    val base: String,
    val target: String,
    val market: Market,
    val last: Double,
    val volume: Double,
    @SerializedName("converted_last") val convertedLast: ConvertedLast,
    @SerializedName("converted_volume") val convertedVolume: ConvertedVolume,
    @SerializedName("trust_score") val trustScore: String,
    @SerializedName("bid_ask_spread_percentage") val bidAskSpreadPercentage: Double,
    val timestamp: String,
    @SerializedName("last_traded_at") val lastTradedAt: String,
    @SerializedName("last_fetch_at") val lastFetchAt: String,
    @SerializedName("is_anomaly") val isAnomaly: Boolean,
    @SerializedName("is_stale") val isStale: Boolean,
    @SerializedName("trade_url") val tradeUrl: String,
    @SerializedName("token_info_url") val tokenInfoUrl: String?,
    @SerializedName("coin_id") val coinId: String,
    @SerializedName("target_coin_id") val targetCoinId: String
)

data class CoinTickerData(
    val name: String,
    val tickers: List<Ticker>
)
