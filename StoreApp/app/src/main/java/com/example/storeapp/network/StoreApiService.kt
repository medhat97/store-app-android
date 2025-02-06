package com.example.storeapp.network

import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET

private const val BASE_URL =
    "http://192.168.22.167:8080/api/auth/test"

private val retrofit = Retrofit.Builder()
    .addConverterFactory(ScalarsConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()

interface StoreApiService {
    @GET("")
    suspend fun getData(): String
}

object StoreApi {
    val retrofitService: StoreApiService by lazy {
        retrofit.create(StoreApiService::class.java)
    }
}