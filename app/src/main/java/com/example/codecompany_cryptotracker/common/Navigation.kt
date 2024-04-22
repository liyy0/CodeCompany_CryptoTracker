package com.example.codecompany_cryptotracker.common

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.codecompany_cryptotracker.Screen
import com.example.codecompany_cryptotracker.presentation.AssetDetail
import com.example.codecompany_cryptotracker.presentation.AssetList
import com.example.codecompany_cryptotracker.presentation.NewsList
import com.example.codecompany_cryptotracker.presentation.SettingScreen
import com.example.codecompany_cryptotracker.presentation.WebViewScreen
import com.example.codecompany_cryptotracker.presentation.Favorite


@Composable
fun Navigation(navController: NavHostController) {
//    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.AssetList.route) {

        composable(route = Screen.AssetList.route
        ) {
            AssetList(navController = navController)
        }
        composable(
            route = Screen.AssetDetail.route + "/{assetId}",
            arguments = listOf(
                navArgument("assetId"){
                    type = NavType.StringType
                    defaultValue = ""}

            )
        ) { backStackEntry ->
            val assetId = backStackEntry.arguments?.getString("assetId")
            AssetDetail(navController = navController,assetId = assetId)
        }

        composable(route = Screen.NewsList.route
        ) {
            NewsList(navController = navController)
        }

        composable("webView/{url}", arguments = listOf(navArgument("url") { type = NavType.StringType })) { backStackEntry ->
            WebViewScreen(url = backStackEntry.arguments?.getString("url") ?: "")
        }

        composable(route = Screen.Settings.route
        ) {
            SettingScreen()
        }

        composable(route=Screen.Favorite.route){
            Favorite(navController = navController)
        }


    }
}

