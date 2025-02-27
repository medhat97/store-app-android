package com.example.storeapp.data

import android.util.Log
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface StoreDao {
    @Query("SELECT * FROM devices")
    fun getAllStores(): Flow<List<StoreEntity>>

    @Query("SELECT * FROM devices WHERE storeNumber = :storeNumber")
    fun getStoresByNumber(storeNumber: String): Flow<List<StoreEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(stores: List<StoreEntity>)

    @Query("UPDATE devices SET deviceStatus = :deviceStatus WHERE deviceName = :deviceName AND deviceSerialNumber= :deviceSerialNumber")
    suspend fun updateDeviceStatus(deviceName:String,deviceSerialNumber: String, deviceStatus: String)

    @Query("SELECT * FROM devices WHERE deviceName LIKE '%' || :searchString || '%'")
    fun searchDevicesByName(searchString: String): Flow<List<StoreEntity>>
}
