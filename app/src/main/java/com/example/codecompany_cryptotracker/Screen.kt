package com.example.codecompany_cryptotracker

sealed class Screen (val route: String) {
    object AssetList: Screen("AssetList")
    object AssetDetail: Screen("AssetDetail")

    fun withArgs(vararg args: String): String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }
}