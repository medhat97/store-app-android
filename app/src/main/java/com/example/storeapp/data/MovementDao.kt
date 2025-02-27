package com.example.storeapp.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface MovementDao {
    @Query("SELECT * FROM movement ORDER BY loanDate ASC")
    fun getAllMovements(): Flow<List<MovementEntity>>

    @Query("SELECT * FROM devices WHERE deviceName = :deviceName AND deviceSerialNumber = :deviceSerialNumber")
    fun getMovementsForDevice(deviceName: String,deviceSerialNumber: String): Flow<StoreEntity>

    @Insert
    suspend fun insertMovement(movement: MovementEntity)

    @Query("UPDATE movement SET returnTime = :returnTime AND returnDate= :returnDate WHERE deviceName = :deviceName AND deviceSerialNumber= :deviceSerialNumber")
    suspend fun updateMovementReturnDateAndTime(deviceName: String, deviceSerialNumber: String,returnTime: String,returnDate: String)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(movements: List<MovementEntity>)
}
