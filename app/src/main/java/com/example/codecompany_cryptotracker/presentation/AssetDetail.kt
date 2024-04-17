package com.example.codecompany_cryptotracker.presentation

import android.graphics.Typeface
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import co.yml.charts.axis.AxisData
import co.yml.charts.common.extensions.formatToSinglePrecision
import co.yml.charts.common.model.Point
import co.yml.charts.ui.linechart.LineChart
import co.yml.charts.ui.linechart.model.Line
import co.yml.charts.ui.linechart.model.LineChartData
import co.yml.charts.ui.linechart.model.LinePlotData
import co.yml.charts.ui.linechart.model.LineStyle
import co.yml.charts.ui.linechart.model.LineType
import co.yml.charts.ui.linechart.model.SelectionHighlightPoint
import co.yml.charts.ui.linechart.model.SelectionHighlightPopUp
import co.yml.charts.ui.linechart.model.ShadowUnderLine
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.codecompany_cryptotracker.data.model.Article
import com.example.codecompany_cryptotracker.data.model.CoinNewsViewModel
import com.example.codecompany_cryptotracker.data.model.MarketChartDataModel
import com.example.codecompany_cryptotracker.data.model.MarketChartDataViewModel
import com.example.codecompany_cryptotracker.network.CoinReposImp
import com.example.codecompany_cryptotracker.network.RetrofitInstance
import com.example.codecompany_cryptotracker.network.RetrofitNewsInstance
import java.net.URLEncoder
import java.text.SimpleDateFormat
import java.util.Date


@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AssetDetail(navController: NavController,assetId: String?) {

    var PriceviewModel = remember{
        if (assetId != null) {
            MarketChartDataViewModel(CoinReposImp(RetrofitInstance.api), assetId)
        }
        else{MarketChartDataViewModel(CoinReposImp(RetrofitInstance.api), "BitCoin")}
    }

    var newsViewModel = remember{
        if (assetId != null) {
            CoinNewsViewModel(CoinReposImp(RetrofitNewsInstance.api), assetId)
        }
        else{CoinNewsViewModel(CoinReposImp(RetrofitNewsInstance.api), "BitCoin")}
    }
    var coinPrice = PriceviewModel.products.collectAsState().value
    var coinNews = newsViewModel.products.collectAsState().value.articles


    val pricesTransformed = coinPrice.prices.mapIndexed { index, array ->
        arrayOf(index + 1, array[1])
    }
    val pricesPoints = pricesTransformed.map {
        Point(it[0].toFloat(), it[1].toFloat())
    }

    val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    val dateData = coinPrice.prices.map{
        sdf.format(Date(it[0].toLong()))
    }


    val market_capsTransformed = coinPrice.market_caps.mapIndexed { index, array ->
        arrayOf(index + 1, array[1])
    }
    val market_capsPoints = market_capsTransformed.map {
        Point(it[0].toFloat(), it[1].toFloat())
    }

    val total_volumesTransformed = coinPrice.total_volumes.mapIndexed { index, array ->
        arrayOf(index + 1, array[1])
    }
    val total_volumespPints = total_volumesTransformed.map {
        Point(it[0].toFloat(), it[1].toFloat())
    }


    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = assetId ?: "Unknown",
                        modifier = Modifier.fillMaxWidth() // Making sure the text takes full width
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
        ) {
            LazyColumn {
                item{
                    Text(
                        text = "Charts",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                }
                item {
                    Chart(pricesPoints, dateData, "Price Chart")
                }
                item {
                    Chart(market_capsPoints, dateData, "Market Cap Chart")
                }
                item {
                    Chart(total_volumespPints, dateData, "Total Volume Chart")
                }
                item {
                    LazyRowForNews(navController = navController, coinNews)
                }
            }



        }
    }

}
@Composable
fun LazyRowForNews(navController: NavController, news: List<Article>) {
    Column(modifier = Modifier.padding(vertical = 12.dp)) {
        Text(
            text = "Latest News",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        if (news.isEmpty()) {
            Text(
                text = "No news available",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(start = 16.dp)
            )
        } else {
            LazyRow(contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp)) {
                items(news) { article ->
                    NewsCard(navController = navController, article = article)
                }
            }
        }
    }
}

@Composable
fun NewsCard(navController: NavController, article: Article) {
    Card(
        modifier = Modifier
            .width(200.dp)
            .padding(8.dp)
            .clickable {
                navController.navigate("webView/${URLEncoder.encode(article.url, "UTF-8")}")
            }
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
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
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = article.title,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = article.description ?: "",
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}


@Composable
fun Chart(points: List<Point>, dateData: List<String>,discription: String){
    if (points.isEmpty()){
        Text("No data available")
    }else{
            Text(
                modifier=Modifier.padding(12.dp),
                text = discription,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            DottedLinechart(points, dateData)
            Spacer(modifier = Modifier.height(12.dp))
            Divider(modifier = Modifier
                .fillMaxWidth()
                .height(1.dp))
        }
}


@Composable
fun DottedLinechart(pointsData: List<Point>,dateData: List<String>) {
    val steps = 5
    val xAxisData = AxisData.Builder()
        .axisStepSize(40.dp)
        .steps(pointsData.size - 1)
        .labelData {"" }
        .labelAndAxisLinePadding(15.dp)
        .axisLineColor(Color(0xFFCCC2DC))
        .build()
    val yAxisData = AxisData.Builder()
        .steps(steps)
        .labelData { i ->
            val yMin = pointsData.minOf { it.y }
            val yMax = pointsData.maxOf { it.y }
            val yScale = (yMax - yMin) / steps
            ((i * yScale) + yMin).formatToSinglePrecision()
        }
        .axisLineColor(Color(0xFFCCC2DC))
        .labelAndAxisLinePadding(20.dp)
        .build()
    val data = LineChartData(
        linePlotData = LinePlotData(
            lines = listOf(
                Line(
                    dataPoints = pointsData,
                    lineStyle = LineStyle(
                        lineType = LineType.SmoothCurve(isDotted = true),
                        color = Color(0xFFD0BCFF)
                    ),
                    shadowUnderLine = ShadowUnderLine(
                        brush = Brush.verticalGradient(
                            listOf(
                                Color(0xFFCCC2DC),
                                Color.Transparent
                            )
                        ), alpha = 0.3f
                    ),
                    selectionHighlightPoint = SelectionHighlightPoint(
                        color = Color(0xFFCCC2DC)
                    ),
                    selectionHighlightPopUp = SelectionHighlightPopUp(
                        backgroundColor = Color.Black, // need to change a color
                        backgroundStyle = Stroke(2f),
                        labelColor = Color(0xFFCCC2DC),
                        labelTypeface = Typeface.DEFAULT_BOLD,
                        popUpLabel = { x, y ->
                            val xLabel = " ${dateData[x.toInt()]} "
                            val yLabel = " ${String.format("%.2f", y)}"
                            "$xLabel : $yLabel"
                        }
                    )
                )
            )
        ),
        xAxisData = xAxisData,
        yAxisData = yAxisData
    )
    LineChart(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp),
        lineChartData = data
    )
}

@Preview
@Composable
fun graphpreview(){
        val prices = arrayOf(
        arrayOf(1712986292444, 67640.02877103467),
        arrayOf(1712986512447, 67502.44550156794),
        arrayOf(1712986809862, 67413.92738081705),
        arrayOf(1712987205024, 67553.83499477942),
        arrayOf(1712987425229, 67484.5419511034),
        arrayOf(1712987709906, 67528.10219970117),
        arrayOf(1712988056035, 67402.31677956472),
        arrayOf(1712988382837, 67462.12660777815),
        arrayOf(1712988675451, 67492.6275078025),
        arrayOf(1712988942112, 67493.31976499925),
        arrayOf(1712989262232, 67278.32864858327),
        arrayOf(1712989532135, 67298.98549494924),
        arrayOf(1712989818276, 67339.97980475398),
        arrayOf(1712990103520, 67307.61569401757),
        arrayOf(1712990430473, 67407.40293988558),
        arrayOf(1712990748774, 67471.08558882782),
        arrayOf(1712991016959, 67357.5685899125),
        arrayOf(1712991352380, 67365.92413195697),
        arrayOf(1712991625606, 67500.54595407809),
        arrayOf(1712991949605, 67514.92273075809),
        arrayOf(1712992219954, 67529.83695113784),
        arrayOf(1712992572708, 67401.29921412093),
        arrayOf(1712992843502, 67239.50810618968),
        arrayOf(1712993113903, 67249.10369450266),
        arrayOf(1712993427643, 67343.63041487713),
        arrayOf(1712993725391, 67376.20946516907),
        arrayOf(1712994072538, 67502.24391428393),
        arrayOf(1712994332106, 67483.55533913216),
        arrayOf(1712994600380, 67445.37407756124),
        arrayOf(1712994991946, 67360.31296351513),
        arrayOf(1712995247504, 67420.29572696098),
        arrayOf(1712995512951, 67395.72383299933),
        arrayOf(1712995853660, 67283.87127088389)
    )

    val transformedPrices = prices.mapIndexed { index, array ->
        arrayOf(index + 1, array[1])
    }
    val points = transformedPrices.map {
        Point(it[0].toFloat(), it[1].toFloat())
    }
    val sdf = SimpleDateFormat("yyyy-MM-dd")
    val dateData = prices.map{
        sdf.format(Date(it[0].toLong()))
    }
    DottedLinechart(points, dateData)


}

@Preview(showBackground = true)
@Composable
fun PreviewAssetDetail() {
//    DeveloperSection()
    var navController = rememberNavController()
    AssetDetail(navController = navController,assetId = "BitCoin")
}