package com.example.codecompany_cryptotracker

sealed class Screen (val route: String) {
    data object AssetList: Screen("AssetList")
    data object AssetDetail: Screen("AssetDetail")
    data object NewsList: Screen("NewsList")
    data object Settings: Screen("Settings")

    data object Favorite: Screen ("Favorite")
}