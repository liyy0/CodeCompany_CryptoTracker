package com.example.codecompany_cryptotracker.data.model

data class MarketChartDataModel(
    val market_caps: List<List<Double>>,
    val prices: List<List<Double>>,
    val total_volumes: List<List<Double>>
)