package com.example.codecompany_cryptotracker.presentation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.codecompany_cryptotracker.data.model.CoinNewsViewModel
import com.example.codecompany_cryptotracker.network.CoinReposImp
import com.example.codecompany_cryptotracker.network.RetrofitNewsInstance
import java.net.URLEncoder

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NewsList(navController: NavController) {

    var newsViewModel = remember{
            CoinNewsViewModel(CoinReposImp(RetrofitNewsInstance.api), "cryptocurrency")
    }
    var coinNews = newsViewModel.products.collectAsState().value.articles

    Column {
        TopAppBar(
            title = {
                Text(
                    text = "Crypto News",
                    textAlign = TextAlign.Center, // Aligning text to the center
                    modifier = Modifier.fillMaxWidth() // Making sure the text takes full width
                )
            }
        )
        LazyColumn {
            items(coinNews.size) { index ->
                val article = coinNews[index]
                Card(
                    modifier = Modifier
                        .padding(8.dp)
                        .clickable {
                            // Use the launcher to open the news URL in a browser
                            navController.navigate("webView/${URLEncoder.encode(article.url, "UTF-8")}")
                        }
                ) {
                    Column {
                        // Load and display the image from the URL
                        article.imageUrl?.let { imageUrl ->
                            AsyncImage(
                                model = ImageRequest.Builder(LocalContext.current)
                                    .data(imageUrl)
                                    .crossfade(true)
                                    .build(),
                                contentDescription = "News Image",
                                modifier = Modifier
                                    .height(200.dp)
                                    .fillMaxWidth(),
                                contentScale = ContentScale.Crop
                            )
                        }
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(text = article.title, style = MaterialTheme.typography.titleLarge)
                            Text(text = article.description ?: "", style = MaterialTheme.typography.bodyLarge)
                        }
                    }
                }
            }
        }
    }


}
@Preview(showBackground = true)
@Composable
fun Previewfun() {
//    DeveloperSection()
    var navController = rememberNavController()
    NewsList(navController = navController)
}
