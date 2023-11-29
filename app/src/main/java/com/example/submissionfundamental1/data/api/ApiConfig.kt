package com.example.submissionfundamental1.data.api

import com.example.submissionfundamental1.BuildConfig
import com.example.submissionfundamental1.BuildConfig.API_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiConfig {

    private val loggingInterceptor = if (BuildConfig.DEBUG) {
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }else {
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE)
    }
    private val client = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()
    private val retrofit = Retrofit.Builder()
        .baseUrl(API_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val gitHubService: ApiService = retrofit.create(ApiService::class.java)

    companion object {
        val apiConfig = ApiConfig()
        val gitHubService = apiConfig.gitHubService
    }
}
