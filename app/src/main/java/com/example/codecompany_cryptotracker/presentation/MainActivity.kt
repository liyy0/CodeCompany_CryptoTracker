package com.example.codecompany_cryptotracker.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

import com.example.codecompany_cryptotracker.ui.theme.CodeCompany_CryptoTrackerTheme
import com.example.codecompany_cryptotracker.util.loadAssets
import com.example.codecompany_cryptotracker.common.Navigation
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val assets = loadAssets(context = applicationContext)
            CodeCompany_CryptoTrackerTheme {
                Navigation()
            }
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

//@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CodeCompany_CryptoTrackerTheme {
        Greeting("Android")
    }
}

