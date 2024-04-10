package com.example.codecompany_cryptotracker.presentation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController


@Composable
fun AssetDetail(assetId: String?) {
    Text(text = "Asset Detail: $assetId")
}