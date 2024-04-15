package com.example.codecompany_cryptotracker.presentation



import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.codecompany_cryptotracker.domain.Asset
import com.example.codecompany_cryptotracker.util.loadAssets


data class Setting(val name: String, val contents: String, val id: Int)
@Composable
fun SettingScreen() {
//    Box(modifier = Modifier.fillMaxSize()){
//        Column(modifier = Modifier
//            .fillMaxSize()
//            .align(Alignment.Center),
//            horizontalAlignment = Alignment.CenterHorizontally,
//            verticalArrangement = Arrangement.Center
//        ) {
//            Text("Settings")
//        }
//    }
    val settingItems = remember {
        listOf(
            Setting("Settings", "This is the setting page,This is the setting page,This is the setting page,This is the setting page,This is the setting page," ,1),
            Setting("About", "This is the setting page", 2),
            Setting("Help", "This is the setting page", 3),
            Setting("Feedback", "This is the setting page", 4),
            Setting("Rate us", "This is the setting page", 5),
            Setting("Share", "This is the setting page", 6),
            Setting("Privacy Policy", "This is the setting page", 7),
            Setting("Terms of Service", "This is the setting page", 8),
            Setting("Open Source Licenses", "This is the setting page", 9),
            Setting("Version", "This is the setting page", 10)
        )
    }
    var expandedItem by remember { mutableStateOf(-1) }


    LazyColumn {
        items(settingItems) { setting ->
                ExpandableCard(content = setting,
                    expanded = expandedItem==setting.id,
                    onClickExpanded = { id ->
                        expandedItem = if (expandedItem == id) {
                            -1
                        } else {
                            id
                        }
                    }
                )
        }
    }
}


@Composable
fun ExpandableCard(content: Setting, expanded: Boolean, onClickExpanded:(id:Int) -> Unit ) {
    val transition = updateTransition(targetState = expanded, label = "trans")

    val iconRotatingDegree by
    transition.animateFloat(label = "iconChange") { state ->
        if (state) {
            0f
        } else {
            180f

        }
    }
    Card() {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Text(text = content.name, modifier = Modifier.weight(1f))
                Icon(imageVector = Icons.Default.KeyboardArrowUp, contentDescription = null,
                    modifier = Modifier
                        .rotate(iconRotatingDegree)
                        .clickable { onClickExpanded(content.id) }
                )
                Spacer(modifier = Modifier.size(16.dp))
                }
            ExpandedContent(isExpanded = expanded, desc = content.contents)
            }
        }

    }

@Composable
fun ExpandedContent(isExpanded: Boolean, desc:String) {

    val enterTransition = remember{
        expandVertically(
            expandFrom = Alignment.Top,
            animationSpec = tween(500)
        )
    }
    val exitTransition = remember{
        shrinkVertically(
            shrinkTowards = Alignment.Top
        )
    }
    AnimatedVisibility(visible = isExpanded,
        enter = enterTransition,
        exit = exitTransition) {
        Text(text = desc, textAlign = TextAlign.Justify)

    }
}




@Preview(showBackground = true)
@Composable
fun PreviewFunc() {
    SettingScreen()
}



