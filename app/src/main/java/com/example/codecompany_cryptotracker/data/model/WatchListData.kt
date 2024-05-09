package com.example.codecompany_cryptotracker.data.model
import android.content.Context
import org.json.JSONArray

//Context is for write to sandbox
class WatchListData (private val context: Context) {

        private val coinIds: MutableList<String> = mutableListOf()

        init {
            loadWatchListFromSandbox()
        }

        // Function to load watchlist from Android sandbox
        private fun loadWatchListFromSandbox() {
            try {
                val inputStream = context.openFileInput("watchlist.json")
                val jsonString = inputStream.bufferedReader().use { it.readText() }
                val jsonArray = JSONArray(jsonString)
                for (i in 0 until jsonArray.length()) {
                    val coinId = jsonArray.getString(i)
                    coinIds.add(coinId)
                }
            } catch (e: Exception) {
                // Handle exceptions here, such as file not found or JSON parsing error
                e.printStackTrace()
            }
        }

        // Function to get the list of coinIds
        fun getCoinIds(): List<String> {
            return coinIds
        }

        // Function to add a coinId to the watchlist
        fun addCoinId(coinId: String) {
            if(!isCoinId(coinId)){
                coinIds.add(coinId)
                saveWatchListToSandbox()
            }

        }

        // Function to remove a coinId from the watchlist
        fun removeCoinId(coinId: String) {
            coinIds.remove(coinId)
            saveWatchListToSandbox()
        }

    // Function to check if a string is a coinId in the coinIds list
    fun isCoinId(coinId: String): Boolean {
        return coinIds.contains(coinId)
    }

        // Function to save watchlist to Android sandbox
        private fun saveWatchListToSandbox() {
            try {
                val jsonArray = JSONArray(coinIds)
                val outputStream = context.openFileOutput("watchlist.json", Context.MODE_PRIVATE)
                outputStream.write(jsonArray.toString().toByteArray())
                outputStream.close()
            } catch (e: Exception) {
                // Handle exceptions here, such as file not found or JSON parsing error
                e.printStackTrace()
            }
        }

    // Function to get the list of coinIds as a string
    fun getCoinIdsAsString(): String {
        return coinIds.joinToString(",")
    }


}