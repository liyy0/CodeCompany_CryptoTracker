package com.example.codecompany_cryptotracker.presentation

import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.codecompany_cryptotracker.data.model.CoinNewsViewModel
import com.example.codecompany_cryptotracker.network.CoinReposImp
import java.net.URLEncoder

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NewsList(navController: NavController) {

    var newsViewModel = remember{
            CoinNewsViewModel(CoinReposImp(RetrofitNewsInstance.api), "cryptocurrency")
    }
    var coinNews = newsViewModel.products.collectAsState().value.articles
    val openUrlLauncher = rememberLauncherForActivityResult(contract = ActivityResultContracts.StartActivityForResult()) { }

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
