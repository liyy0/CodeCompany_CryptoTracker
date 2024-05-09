package com.example.codecompany_cryptotracker.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
// RetrofitInstance is a singleton class that creates a Retrofit instance
object RetrofitInstance {

        private val interceptor: HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        private val client: OkHttpClient = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()

        val api: ApiInterface= Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(ApiInterface.BASE_URL)
            .client(client)
            .build()
            .create(ApiInterface::class.java)

    }