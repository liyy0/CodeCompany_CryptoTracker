package com.example.codecompany_cryptotracker.presentation



import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.Image
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
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.codecompany_cryptotracker.R
import com.example.codecompany_cryptotracker.data.local.Developer
import com.example.codecompany_cryptotracker.domain.Asset
import com.example.codecompany_cryptotracker.util.loadAssets


data class Setting(val name: String, val contents: String, val id: Int)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingScreen() {

    val settingItems = remember {
        listOf(
            Setting("Developers", "Developers of the App:", 0),
            Setting("Feedback", "Please give us some feedbacks.", 1),
            Setting("Rate us", "Rate us here:", 2),

        )
    }
    var expandedItem by remember { mutableStateOf(0) }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        TopAppBar(
            title = { Text(text = "Crypto Tracker",style = MaterialTheme.typography.titleLarge,) },
//            backgroundColor = MaterialTheme.colors.primary,
            modifier = Modifier.fillMaxWidth()
        )
        Column(
            modifier = Modifier.weight(1f)
        ) {


            LazyColumn {

                items(settingItems) { setting ->
                    ExpandableCard(
                        content = setting,
                        expanded = expandedItem == setting.id,
                        onClickExpanded = { id ->
                            expandedItem = if (expandedItem == id) {
                                -1
                            } else {
                                id
                            }
                        }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
        FooterSection()
    }

}
@Composable
fun DeveloperSection(){
    val developer = listOf(
        Developer(
            name = "Shuaiqi Huang",
            email = "sqh@example.com",
            profilePictureResId = R.drawable.hsq
        ),
        Developer(
            name = "Yuyan Li",
            email = "liyy@example.com",
            profilePictureResId = R.drawable.lyy),
        Developer(
            name = "Zhangde Song",
            email = "szd@example.com",
            profilePictureResId = R.drawable.szd),

        )
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(text = "Developers", style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(8.dp))
        developer.forEach {
            DeveloperItem(developer = it)
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun DeveloperItem(developer: Developer ) {

    Row(
//        modifier = Modifier.padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(developer.profilePictureResId),
            contentDescription = null,
            modifier = Modifier.size(64.dp),
            contentScale = ContentScale.Crop
        )
        Column(
            modifier = Modifier.padding(start = 16.dp)
        ) {
            Text(text = developer.name)
            Text(text = developer.email)
        }
    }
}

@Composable
fun FooterSection() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Divider(color = Color.Gray, thickness = 1.dp)
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Version: 1.0.0",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "Release Date: April 17, 2024",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Privacy Policy",
            style = MaterialTheme.typography.labelMedium,
//            color = MaterialTheme.colors.primary,
//            modifier = Modifier.clickable { /* Handle privacy policy click */ }
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "Terms of Service",
            style = MaterialTheme.typography.labelMedium,
//            color = MaterialTheme.colors.primary,
//            modifier = Modifier.clickable { /* Handle terms of service click */ }
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "© 2024 Crypto Tracker. All rights reserved.",
            style = MaterialTheme.typography.labelSmall,
            color = Color.Gray,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
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
        if (desc == "Developers of the App:"){
            DeveloperSection()}
        else {
            Text(text = desc, textAlign = TextAlign.Justify)
        }
    }
}




@Preview(showBackground = true)
@Composable
fun PreviewFunc() {
//    DeveloperSection()
    SettingScreen()
}



