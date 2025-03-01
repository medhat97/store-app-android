package com.example.storeapp.network

import com.example.storeapp.model.MovementRecord
import com.example.storeapp.model.StoreRecord
import com.example.storeapp.model.StoreUsers
import com.example.storeapp.model.StoresList
import com.example.storeapp.model.UpdateDeviceStatus
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

private const val BASE_URL =
    "http://192.168.22.105:3000/"

private val retrofit = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()

interface StoreApiService {
    @GET("devices")
    suspend fun getAllData():List<StoreRecord>

    @GET("devices")
    suspend fun getData(@Query("storeNumber") storeNumber: String ): List<StoreRecord>

    @GET("stores")
    suspend fun getStoreData(): List<StoresList>

    @GET("movement")
    suspend fun getMovements(): List<MovementRecord>

    @GET("users")
    suspend fun getAllUsers(): List<StoreUsers>

    @POST("movement")
    suspend fun addMovement(@Body movement:MovementRecord ): MovementRecord

    @PATCH("devices/{id}")
    suspend fun updateDeviceStatus(
        @Path("id") id: String,
        @Body updateRequest: UpdateDeviceStatus
    ): StoreRecord
}

object StoreApi {
    val retrofitService: StoreApiService by lazy {
        retrofit.create(StoreApiService::class.java)
    }
}