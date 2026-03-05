package com.example.pokeapp.data.remote

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit


object NetworkModule {
    private const val BASE_URL = "https://pokeapi.co/api/v2/"

    private val json = Json{
        ignoreUnknownKeys = true
        isLenient = true
    }
    private val okHttp: OkHttpClient by lazy{
        val logger = HttpLoggingInterceptor().apply {
            level =  HttpLoggingInterceptor.Level.BODY
        }
        OkHttpClient.Builder()
            .addInterceptor(logger)
            .build()
    }
    val api: PokeApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttp)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
            .create(PokeApiService::class.java)
    }
}