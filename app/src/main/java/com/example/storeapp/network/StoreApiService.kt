package com.example.storeapp.network

import com.example.storeapp.model.StoreRecord
import com.example.storeapp.model.StoresList
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

private const val BASE_URL =
    "http://192.168.22.132:3000/"

private val retrofit = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()

interface StoreApiService {
    @GET("devices")
    suspend fun getData(@Query("store_id") store_id: String ): List<StoreRecord>
    @GET("stores")
    suspend fun getStoreData(): List<StoresList>
}

object StoreApi {
    val retrofitService: StoreApiService by lazy {
        retrofit.create(StoreApiService::class.java)
    }
}