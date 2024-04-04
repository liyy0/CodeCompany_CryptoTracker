package com.example.codecompany_cryptotracker.util

import android.content.Context
import com.example.codecompany_cryptotracker.R
import com.example.codecompany_cryptotracker.domain.Asset
import kotlinx.serialization.json.Json
import java.io.InputStream


fun loadAssets(context: Context): List<Asset> {
    val inputStream: InputStream = context.resources.openRawResource(R.raw.asset_list_with_icons)
    val jsonString = inputStream.bufferedReader().use { it.readText() }
    val jsonParser = Json { ignoreUnknownKeys = true } // Configure the JSON parser to ignore unknown keys
    return jsonParser.decodeFromString(jsonString)
}