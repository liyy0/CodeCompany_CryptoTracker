package com.example.codecompany_cryptotracker.presentation

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CurrencyExchange
import androidx.compose.material.icons.filled.Newspaper
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.CurrencyExchange
import androidx.compose.material.icons.outlined.Newspaper
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold

import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModel
import androidx.navigation.compose.rememberNavController
import com.example.codecompany_cryptotracker.R


import com.example.codecompany_cryptotracker.ui.theme.CodeCompany_CryptoTrackerTheme
import com.example.codecompany_cryptotracker.common.Navigation
import com.example.codecompany_cryptotracker.data.model.CoinNameItem
import com.example.codecompany_cryptotracker.data.model.CoinNameViewModel
import com.example.codecompany_cryptotracker.data.model.MarketChartDataViewModel
import com.example.codecompany_cryptotracker.data.model.WatchListData
import com.example.codecompany_cryptotracker.network.CoinReposImp
import kotlinx.coroutines.flow.collect

data class BottomNavigationItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val route: String = ""
)
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val context = applicationContext
        val watchList= WatchListData(context)

//
//        var coinNameList = CoinNameViewModel(CoinReposImp(RetrofitInstance.api))
//        var coinNamePrice = MarketChartDataViewModel(CoinReposImp(RetrofitInstance.api), "bitcoin")
        setContent {
//            CodeCompany_CryptoTrackerTheme {
//                Text(text = "Hello World")
//                var str = coinNamePrice.products.collectAsState().value.toString()
//                Text(text = str)
                BottomNavigation(watchList)
            }
        }
    }

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomNavigation(
    watchList: WatchListData
){
    val navController = rememberNavController()
    var selectedItemIndex by rememberSaveable {
        mutableStateOf(0)
    }
    val items = listOf(
        BottomNavigationItem(
            title = stringResource(R.string.coins),
            selectedIcon = Icons.Filled.CurrencyExchange,
            unselectedIcon = Icons.Outlined.CurrencyExchange,
            route = "AssetList"
        ),
        BottomNavigationItem(
            title = stringResource(R.string.news),
            selectedIcon = Icons.Filled.Newspaper,
            unselectedIcon = Icons.Outlined.Newspaper,
            route = "NewsList"
        ),
        BottomNavigationItem(
            title = stringResource(R.string.favorite),
            selectedIcon = Icons.Filled.Star,
            unselectedIcon = Icons.Outlined.Star,
            route = "Favorite"
        ),
        BottomNavigationItem(
            title = stringResource(R.string.settings),
            selectedIcon = Icons.Filled.Settings,
            unselectedIcon = Icons.Outlined.Settings,
            route = "Settings"
        ),

    )
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    Scaffold (
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),

        bottomBar = {
            NavigationBar{
                items.forEachIndexed{ index, item ->
                    NavigationBarItem(
                        selected = selectedItemIndex == index,
                        onClick = {
                            selectedItemIndex = index
                            navController.navigate(item.route)
                        },
                        label = {
                            Text(text = item.title)
                        },
                        icon = {
                            Icon(
                                imageVector = if (selectedItemIndex == index) item.selectedIcon else item.unselectedIcon,
                                contentDescription = item.title
                            )
                        },
                    )
                }
            }
        }
    ){paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            Navigation(navController, watchList)
        }
    }
}



@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CodeCompany_CryptoTrackerTheme {
//        BottomNavigation()
    }
}

