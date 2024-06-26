package com.example.codecompany_cryptotracker.presentation

import android.graphics.Typeface
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import co.yml.charts.axis.AxisData
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
import com.example.codecompany_cryptotracker.R
import com.example.codecompany_cryptotracker.data.model.Article
import com.example.codecompany_cryptotracker.data.model.CoinData
import com.example.codecompany_cryptotracker.data.model.CoinDataViewModel
import com.example.codecompany_cryptotracker.data.model.CoinNameItem
import com.example.codecompany_cryptotracker.data.model.CoinNameViewModel
import com.example.codecompany_cryptotracker.data.model.CoinNewsViewModel
import com.example.codecompany_cryptotracker.data.model.CoinTickerData
import com.example.codecompany_cryptotracker.data.model.CoinTickerViewModel
import com.example.codecompany_cryptotracker.data.model.MarketChartDataViewModel
import com.example.codecompany_cryptotracker.network.CoinReposImp
import com.example.codecompany_cryptotracker.network.RetrofitInstance
import com.example.codecompany_cryptotracker.network.RetrofitNewsInstance
import java.net.URLEncoder
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AssetDetail(navController: NavController,assetId: String?, assetName:String?) {
    // Get the current locale and set the language to Chinese if the locale is Chinese
    val configuration = LocalConfiguration.current
    val locale = configuration.locales.get(0) ?: Locale.getDefault()
    var language:String = "en"

    Log.d("Debug-Locale", "${locale.language}")
    if(locale.language.toString() == "zh"){
        language = "zh"
    }

    //make network request to get the data
    var PriceviewModel = remember{
        if (assetId != null) {
            MarketChartDataViewModel(CoinReposImp(RetrofitInstance.api), assetId)
        }
        else{MarketChartDataViewModel(CoinReposImp(RetrofitInstance.api), "BitCoin")}
    }

    var CoinTickerViewModel = remember{
        if (assetId != null) {
            CoinTickerViewModel(CoinReposImp(RetrofitInstance.api), assetId)
        }
        else{CoinTickerViewModel(CoinReposImp(RetrofitInstance.api), "BitCoin")}
    }

    var newsViewModel = remember{
        if (assetName != null) {
            CoinNewsViewModel(CoinReposImp(RetrofitNewsInstance.api), assetName, language = language)
        }
        else{CoinNewsViewModel(CoinReposImp(RetrofitNewsInstance.api), "BitCoin")}
    }

    var marketViewModel = remember{
        CoinNameViewModel(CoinReposImp(RetrofitInstance.api), assetId)
    }

    //collect the data from the viewmodel
    val coinMarketData1 by marketViewModel.products.collectAsState()
    val coinMarketData = coinMarketData1.firstOrNull()
    var coinPrice = PriceviewModel.products.collectAsState().value
    var coinNews = newsViewModel.products.collectAsState().value.articles
    var coinTicker = CoinTickerViewModel.products.collectAsState().value

    //transform the data
    val pricesTransformed = coinPrice.prices.map {
        it[1]
    }

    val sdf = SimpleDateFormat("MM-dd")
    val dateData = coinPrice.prices.map{
        sdf.format(Date(it[0].toLong()))
    }

    val total_volumesTransformed = coinPrice.total_volumes.map {
        it[1]
    }

    var daterange = remember {
        mutableStateOf(7)
    }

    val radioOptions = listOf(stringResource(R.string.week),
        stringResource(R.string.month), stringResource(R.string.longer)
    )
    val (selectedOption, onOptionSelected) = remember { mutableStateOf(radioOptions[0]) }


    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = assetName ?: stringResource(R.string.unknown),
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
                    if (coinMarketData != null) {
                        CoinDetail(assetName,coinTicker,navController)
                    }
                }
                item {
                    if (coinMarketData != null) {
                        CryptoInfoCard(coin = coinMarketData)
                    }
                }

                item{
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
                        Text(
                            text = stringResource(R.string.market),
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.End // Align radio buttons to the end
                        ) {
                            radioOptions.forEach { text ->
                                Row(
                                    Modifier
                                        .height(36.dp)
                                        .selectable(
                                            selected = (text == selectedOption),
                                            onClick = {
                                                onOptionSelected(text)
                                                // Update daterange based on the selected option
                                                daterange.value = when (text) {
                                                    "week" -> 7
                                                    "month" -> 30
                                                    "Longer" -> 90
                                                    "周" -> 7
                                                    "月" -> 30
                                                    "更长时间" -> 90
                                                    else -> 7
                                                }
                                            },
                                            role = Role.RadioButton
                                        )
                                        .padding(horizontal = 5.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    RadioButton(
                                        selected = (text == selectedOption),
                                        onClick = null // null recommended for accessibility with screenreaders
                                    )
                                    Text(
                                        text = text,
                                        style = MaterialTheme.typography.titleMedium,
                                        modifier = Modifier.padding(start = 4.dp)
                                    )
                                }
                            }
                        }
                    }
                }
                item {
                    Chart(pricesTransformed,total_volumesTransformed, dateData,daterange)
                }
                item {
                    LazyRowForNews(navController = navController, coinNews)
                }
            }
        }
    }

}


// Top section of the asset detail screen
@Composable
fun CoinDetail(assetName: String?, coinTicker: CoinTickerData, navController: NavController) {
    // Extract trade URL from ticker data
    var tradeUrl: String? = coinTicker.tickers.firstOrNull()?.tradeUrl

    // Column to organize UI elements vertically
    Column {
        // Row to organize UI elements horizontally
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Column for displaying asset name
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = assetName ?: stringResource(R.string.unknown),
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
            }
            // Column for trade button and "no trade available" message
            Column{
                // Trade button
                Button(
                    onClick = {
                        if (tradeUrl != null) {
                            // Navigate to web view with encoded trade URL
                            navController.navigate("webView/${URLEncoder.encode(tradeUrl, "UTF-8")}")
                        }
                    },
                    modifier = Modifier.padding(start = 8.dp),
                    enabled = tradeUrl != null  // Disable button if tradeUrl is null
                ) {
                    Text(text = stringResource(R.string.trade))
                }
                // Display "no trade available" message if tradeUrl is null
                if(tradeUrl == null){
                    Text(
                        text = stringResource(R.string.no_trade_available),
                        style = TextStyle(color = Color.Red)
                    )
                }
            }
        }
        // Spacer for adding space between elements
        Spacer(modifier = Modifier.height(12.dp))
    }
}

//News section of the asset detail screen
@Composable
fun LazyRowForNews(navController: NavController, news: List<Article>) {
    Column(modifier = Modifier.padding(vertical = 12.dp)) {
        Text(
            text = stringResource(R.string.latest_news),
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        if (news.isEmpty()) {
            Text(
                text = stringResource(R.string.no_news_available),
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

//News card for the news section lazyColumn
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
// Crypto info card for the asset detail screen
fun simplifyValue(value: Float): String {
    return when {
        value >= 1e12 -> String.format("%.2fT", value / 1e12)
        value >= 1e9 -> String.format("%.2fB", value / 1e9)
        value >= 1000000 -> String.format("%.1fM", value / 1000000)
        value >= 1000 -> String.format("%.1fK", value / 1000)
        value >= 1 -> String.format("%.2f", value)
        value >= 0.001 -> String.format("%.2f", value)
        value >= 0.000001 -> String.format("%.2fu", value * 1000000)
        else -> String.format("%.2fn", value * 1000000000)
    }
}
// Crypto info card for the asset detail screen
fun formatNumberWithCommas(number: Float): String {
    val numberString = number.toString()
    val parts = numberString.split(".")
    val integerPart = parts[0]
    val fractionalPart = if (parts.size > 1) "." + parts[1] else ""

    val reversedIntegerPart = integerPart.reversed()
    val formattedString = StringBuilder()

    for (i in reversedIntegerPart.indices) {
        formattedString.append(reversedIntegerPart[i])
        if ((i + 1) % 3 == 0 && (i + 1) != reversedIntegerPart.length) {
            formattedString.append(',')
        }
    }
    return formattedString.reverse().toString() + fractionalPart
}

// Crypto info card for the asset detail screen
// chart for the asset detail screen
@Composable
fun Chart(
    pricesTransformed: List<Double>,
    total_volumesTransformed: List<Double>,
    dateData: List<String>,
    daterange: MutableState<Int>,){
    var input_pricesTransformed:List<Point> = emptyList()
    var input_volumnTransformed:List<Point> = emptyList()
    var input_dateData:List<String> = emptyList()
    if (pricesTransformed.isEmpty() or total_volumesTransformed.isEmpty()){
        Text(stringResource(R.string.no_data_available))
    }else{
        //Different time period display
        if (daterange.value == 7 && pricesTransformed.size >= 7 && total_volumesTransformed.size >=7 && dateData.size >= 7){
            input_pricesTransformed = pricesTransformed.subList(pricesTransformed.size-7,pricesTransformed.size).mapIndexed { index, value ->
                Point(index.toFloat(), value.toFloat())
            }
            input_volumnTransformed = total_volumesTransformed.subList(total_volumesTransformed.size-7,total_volumesTransformed.size).mapIndexed { index, value ->
                Point(index.toFloat(), value.toFloat())
            }
            input_dateData = dateData.subList(dateData.size-7,dateData.size)
        }else if (daterange.value == 30 && pricesTransformed.size >= 30 && total_volumesTransformed.size >=7 && dateData.size >= 30){
            input_pricesTransformed = pricesTransformed.subList(pricesTransformed.size-30,pricesTransformed.size).mapIndexed { index, value ->
                Point(index.toFloat(), value.toFloat())
            }
            input_volumnTransformed = total_volumesTransformed.subList(total_volumesTransformed.size-30,total_volumesTransformed.size).mapIndexed { index, value ->
                Point(index.toFloat(), value.toFloat())
            }
            input_dateData = dateData.subList(dateData.size-30,dateData.size)
        }else if (daterange.value == 90 && pricesTransformed.size >= 90 && total_volumesTransformed.size >= 90 && dateData.size >= 90){
            input_pricesTransformed = pricesTransformed.mapIndexed { index, value ->
                Point(index.toFloat(), value.toFloat())
            }
            input_volumnTransformed = total_volumesTransformed.mapIndexed { index, value ->
                Point(index.toFloat(), value.toFloat())
            }
            input_dateData = dateData
        }
//        Text(input_points.toString())
        Text(
            text = stringResource(R.string.price_detail),
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = stringResource(R.string.price_description),
            style = MaterialTheme.typography.bodyLarge
        )
        DottedLinechart(input_pricesTransformed, input_dateData,daterange.value)
        Spacer(modifier = Modifier.height(12.dp))
        Divider(modifier = Modifier
            .fillMaxWidth()
            .height(1.dp))
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = stringResource(R.string.total_volume),
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = stringResource(R.string.volume_description),
            style = MaterialTheme.typography.bodyLarge
        )
        DottedLinechart(input_volumnTransformed, input_dateData,daterange.value)
        Spacer(modifier = Modifier.height(12.dp))
        Divider(modifier = Modifier
            .fillMaxWidth()
            .height(1.dp))
        Spacer(modifier = Modifier.height(12.dp))
    }
}
//chart for the asset detail screen
@Composable
fun DottedLinechart(pointsData: List<Point>,dateData: List<String>,daterange: Int) {
    var stepSize = 10.dp
    if (daterange == 7){
        stepSize = 50.dp
    } else if (daterange == 30){
        stepSize = 20.dp
    }
    val steps = 5
    val xAxisData = AxisData.Builder()
        .axisStepSize(stepSize)
        .steps(pointsData.size - 1)
//        .labelData { x-> dateData[x]}
        .labelData {i ->
               if (daterange == 7){
                   dateData[i]}
                else{
                    ""
                }
            }
        .labelAndAxisLinePadding(6.dp)
        .axisLabelColor(Color(0xFFCCC2DC))
        .axisLineColor(Color(0xFFCCC2DC))
        .build()
    val yAxisData = AxisData.Builder()
        .steps(steps)
        .labelData { i ->
            val yMin = pointsData.minOf { it.y }
            val yMax = pointsData.maxOf { it.y }
            val yScale = (yMax - yMin) / steps
//            ((i * yScale) + yMin).formatToSinglePrecision()
            simplifyValue((i * yScale) + yMin)
        }
        .axisLineColor(Color(0xFFCCC2DC))
        .labelAndAxisLinePadding(10.dp)
        .axisLabelColor(Color(0xFFCCC2DC))
        .build()
    val data = LineChartData(
        backgroundColor = Color.Transparent,
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
                        backgroundColor = Color.Transparent, // need to change a color
                        backgroundStyle = Stroke(2f),
                        labelColor = Color(0xFFCCC2DC),
                        labelTypeface = Typeface.DEFAULT_BOLD,
                        popUpLabel = { x, y ->
                            try {
                                val xLabel = "${dateData[x.toInt()]}"
                                val yLabel = "${formatNumberWithCommas(y)}"
                                "$xLabel : $yLabel"
                            } catch (e: Exception) {
                                Log.d("Error", e.toString())
                                ""
                            }
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

// preview for testing
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
    DottedLinechart(points, dateData,90)


}

@Preview(showBackground = true)
@Composable
fun PreviewAssetDetail() {
//    DeveloperSection()
    var navController = rememberNavController()
    AssetDetail(navController = navController,assetId = "BitCoin", assetName = "BitCoin")
}