package com.example.codecompany_cryptotracker.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
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
            AssetDetail(assetId = assetId)
        }

        composable(route = Screen.NewsList.route
        ) {
            NewsList()
        }

        composable(route = Screen.Settings.route
        ) {
            SettingScreen()
        }




    }
}

@Composable
fun setVersion(){
    Box(modifier = Modifier.fillMaxSize()){
        Column(modifier = Modifier
            .fillMaxSize()
            .align(Alignment.BottomCenter),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom
        ) {
            Text("Current Version: 0.0.0")
        }
    }
}

@Composable
fun setSettings(){
    val settingsList = listOf("Languages")
    LazyColumn {
        items(settingsList){setting ->
            SettingsItem(
                setting = setting,
                onItemClick = {

                }
            )
        }
    }
}

@Composable
fun SettingsItem(setting: String, onItemClick:() -> Unit) {
    Box(
        modifier = Modifier
            .clickable { onItemClick() }
            .padding(16.dp)
    ) {
        Text(text = setting)
    }
}

