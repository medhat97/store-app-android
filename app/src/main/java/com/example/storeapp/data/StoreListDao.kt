package com.example.storeapp.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.storeapp.model.StoresList
import kotlinx.coroutines.flow.Flow

@Dao
interface StoreListDao {
    @Query("SELECT * FROM stores")
    fun getStores(): Flow<List<StoresList>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(stores: List<StoreListEntity>)
}