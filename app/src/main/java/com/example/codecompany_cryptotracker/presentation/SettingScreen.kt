package com.example.codecompany_cryptotracker.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.codecompany_cryptotracker.R
import com.example.codecompany_cryptotracker.data.local.Developer
data class Setting(val name: String, val contents: String, val id: Int)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingScreen() {
    val developerstring = stringResource(R.string.developers)
    val developer_content = stringResource(R.string.developers_of_the_app)
    val feedback = stringResource(R.string.feedback)
    val feedback_content = stringResource(R.string.please_give_us_some_feedbacks)
    val rate = stringResource(R.string.rate_us)
    val rate_content = stringResource(R.string.rate_us_here)
    val settingItems = remember {
        listOf(
            Setting(developerstring, developer_content, 0),
            Setting(feedback, feedback_content, 1),
            Setting(rate, rate_content, 2),
        )
    }
    var expandedItem by remember {mutableStateOf(0)}

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        TopAppBar(
            title = { Text(text = stringResource(R.string.app_name),style = MaterialTheme.typography.titleLarge,) },
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

// Developer section of the setting screen
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
        modifier = Modifier
            .padding(horizontal = 12.dp)
            .fillMaxWidth()
    ) {
        Text(text = stringResource(R.string.developers_of_the_app)
            , style = MaterialTheme.typography.titleSmall)
        Spacer(modifier = Modifier.height(8.dp))
        developer.forEach {
            DeveloperItem(developer = it)
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

// Developer info in the setting screen
@Composable
fun DeveloperItem(developer: Developer ) {
    Row(
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

// Footer section of the setting screen
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
            text = stringResource(R.string.version_1_0_0),
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = stringResource(R.string.release_date_april_17_2024),
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = stringResource(R.string.terms_of_service),
            style = MaterialTheme.typography.labelMedium,
//            color = MaterialTheme.colors.primary,
//            modifier = Modifier.clickable { /* Handle terms of service click */ }
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = stringResource(R.string._2024_crypto_tracker_all_rights_reserved),
            style = MaterialTheme.typography.labelSmall,
            color = Color.Gray,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
    }
}


// Expandable card composable function
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

// content of the expanded card
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
        if ((desc == "Developers of the App:") or (desc == "开发团队")){
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



