package com.example.codecompany_cryptotracker.presentation

import android.graphics.Typeface
import android.os.Build
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
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.Role
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
import com.example.codecompany_cryptotracker.data.model.Article
import com.example.codecompany_cryptotracker.data.model.CoinDataViewModel
import com.example.codecompany_cryptotracker.data.model.CoinNewsViewModel
import com.example.codecompany_cryptotracker.data.model.CoinTickerViewModel
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

    var CoinDataViewModel = remember{
        if (assetId != null) {
            CoinDataViewModel(CoinReposImp(RetrofitInstance.api), assetId)
        }
        else{CoinDataViewModel(CoinReposImp(RetrofitInstance.api), "BitCoin")}
    }

    var CoinTickerViewModel = remember{
        if (assetId != null) {
            CoinTickerViewModel(CoinReposImp(RetrofitInstance.api), assetId)
        }
        else{CoinTickerViewModel(CoinReposImp(RetrofitInstance.api), "BitCoin")}
    }

    var newsViewModel = remember{
        if (assetId != null) {
            CoinNewsViewModel(CoinReposImp(RetrofitNewsInstance.api), assetId)
        }
        else{CoinNewsViewModel(CoinReposImp(RetrofitNewsInstance.api), "BitCoin")}
    }
    var coinPrice = PriceviewModel.products.collectAsState().value
    var coinNews = newsViewModel.products.collectAsState().value.articles
    var coinData = CoinDataViewModel.products.collectAsState().value
    var coinTicker = CoinTickerViewModel.products.collectAsState().value


//    val pricesTransformed = coinPrice.prices.mapIndexed { index, array ->
//        arrayOf(index, array[1])
//    }

    val pricesTransformed = coinPrice.prices.map {
        it[1]
    }


    val sdf = SimpleDateFormat("MM-dd")
    val dateData = coinPrice.prices.map{
        sdf.format(Date(it[0].toLong()))
    }



    val total_volumesTransformed = coinPrice.prices.map {
        it[1]
    }

    var daterange = remember {
        mutableStateOf(7)
    }



    val radioOptions = listOf("week", "month", "Longer")
    val (selectedOption, onOptionSelected) = remember { mutableStateOf(radioOptions[0]) }




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
//            Text(text = pricesPoints.size.toString())

            LazyColumn {
                item{
                    DetailInfo(assetId)
                }

                item{
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
                        Text(
                            text = "History Data",
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
                                        style = MaterialTheme.typography.bodySmall,
                                        modifier = Modifier.padding(start = 4.dp)
                                    )
                                }
                            }
                        }
                    }



                }
                item {

                }
                item {
                    Chart(pricesTransformed, dateData,daterange,"Price Chart","The current value of that cryptocurrency in terms of another currency. It represents the rate at which one unit of the cryptocurrency can be exchanged for another currency at a given moment in time.")
                }
                item {
                    Chart(total_volumesTransformed, dateData,daterange,"Total Volume Chart","The overall amount of trading activity, showing how much of the cryptocurrency has been bought and sold within a specific time frame.")
                }
                item {
                    LazyRowForNews(navController = navController, coinNews)
                }
            }



        }
    }

}



@Composable
fun DetailInfo(assetId: String?){
    Text(
        text = "Place Holder for ${assetId}",
        style = MaterialTheme.typography.titleLarge,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(bottom = 8.dp)
    )
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
@Composable
fun Chart(
    values: List<Double>,
    dateData: List<String>,
    daterange: MutableState<Int>,
    title: String,
    description: String){
    var input_points:List<Point> = emptyList()
    var input_dateData:List<String> = emptyList()
    if (values.isEmpty()){
        Text("No data available")
    }else{
        if (daterange.value == 7 && values.size >= 7 && dateData.size >= 7){
            input_points = values.subList(values.size-7,values.size).mapIndexed { index, value ->
                Point(index.toFloat(), value.toFloat())
            }
            input_dateData = dateData.subList(dateData.size-7,dateData.size)
        }else if (daterange.value == 30 && values.size >= 30 && dateData.size >= 30){
            input_points = values.subList(values.size-30,values.size).mapIndexed { index, value ->
                Point(index.toFloat(), value.toFloat())
            }
            input_dateData = dateData.subList(dateData.size-30,dateData.size)
        }else if (daterange.value == 90 && values.size >= 90 && dateData.size >= 90){
            input_points = values.mapIndexed { index, value ->
                Point(index.toFloat(), value.toFloat())
            }
            input_dateData = dateData
        }
//        Text(input_points.toString())
        Text(
            modifier=Modifier.padding(12.dp),
            text = title,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )
        Text(
            modifier=Modifier.padding(12.dp),
            text = description,
            style = MaterialTheme.typography.bodySmall
        )
        DottedLinechart(input_points, input_dateData,daterange.value)
        Spacer(modifier = Modifier.height(12.dp))
        Divider(modifier = Modifier
            .fillMaxWidth()
            .height(1.dp))
    }
}

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
                            val xLabel = " ${dateData[x.toInt()]} "
                            val yLabel = " ${formatNumberWithCommas(y)}"
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
//        arrayOf(1712988056035, 67402.31677956472),
//        arrayOf(1712988382837, 67462.12660777815),
//        arrayOf(1712988675451, 67492.6275078025),
//        arrayOf(1712988942112, 67493.31976499925),
//        arrayOf(1712989262232, 67278.32864858327),
//        arrayOf(1712989532135, 67298.98549494924),
//        arrayOf(1712989818276, 67339.97980475398),
//        arrayOf(1712990103520, 67307.61569401757),
//        arrayOf(1712990430473, 67407.40293988558),
//        arrayOf(1712990748774, 67471.08558882782),
//        arrayOf(1712991016959, 67357.5685899125),
//        arrayOf(1712991352380, 67365.92413195697),
//        arrayOf(1712991625606, 67500.54595407809),
//        arrayOf(1712991949605, 67514.92273075809),
//        arrayOf(1712992219954, 67529.83695113784),
//        arrayOf(1712992572708, 67401.29921412093),
//        arrayOf(1712992843502, 67239.50810618968),
//        arrayOf(1712993113903, 67249.10369450266),
//        arrayOf(1712993427643, 67343.63041487713),
//        arrayOf(1712993725391, 67376.20946516907),
//        arrayOf(1712994072538, 67502.24391428393),
//        arrayOf(1712994332106, 67483.55533913216),
//        arrayOf(1712994600380, 67445.37407756124),
//        arrayOf(1712994991946, 67360.31296351513),
//        arrayOf(1712995247504, 67420.29572696098),
//        arrayOf(1712995512951, 67395.72383299933),
//        arrayOf(1712995853660, 67283.87127088389)
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
    AssetDetail(navController = navController,assetId = "BitCoin")
}