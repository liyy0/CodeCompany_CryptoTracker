package com.example.codecompany_cryptotracker.data.model

import com.google.gson.annotations.SerializedName
// Data Class for Coin Information from CoinGecko API
// For News Display in Coins Detail Screen and News Screen
data class CoinNews(
    val status: String,
    @SerializedName("totalResults") val totalResults: Int,
    val articles: List<Article>
)

data class Article(
    val source: Source,
    val author: String?,
    val title: String,
    val description: String?,
    val url: String,
    @SerializedName("urlToImage") val imageUrl: String?,
    @SerializedName("publishedAt") val publishedAt: String,
    val content: String?
)

data class Source(
    val id: String?,
    val name: String
)
