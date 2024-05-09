package com.example.codecompany_cryptotracker.network


// Clearly showing the result of the network call
sealed class Result<T>(
    val data: T? = null,
    val message: String? = null
) {
    class Success<T> (data: T?): Result<T>(data)
    class Error<T> (data: T? = null, message: String): Result<T>(data, message)
}