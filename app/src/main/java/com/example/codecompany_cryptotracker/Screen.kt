package com.example.codecompany_cryptotracker

sealed class Screen (val route: String) {
    data object AssetList: Screen("AssetList")
    data object AssetDetail: Screen("AssetDetail")
    data object NewsList: Screen("NewsList")
    data object NewsDetail: Screen("NewsDetail")
    data object Settings: Screen("Settings")

    data object Favorite: Screen ("Favorite")


    fun withArgs(vararg args: String): String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }
}