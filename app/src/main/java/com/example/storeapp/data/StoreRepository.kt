package com.example.storeapp.data

import android.util.Log
import com.example.storeapp.model.MovementRecord
import com.example.storeapp.model.StoreRecord
import com.example.storeapp.model.StoreUsers
import com.example.storeapp.model.UpdateDeviceStatus
import com.example.storeapp.network.StoreApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class StoreRepository(
    private val storeDao: StoreDao,
    private val movementDao: MovementDao,
    private val storeListDao: StoreListDao,
    private val userDao: UserDao
) {
    // Store operations
    val allStores: Flow<List<StoreRecord>> = storeDao.getAllStores()
        .map { entities -> entities.map { StoreEntity.toStoreRecord(it) } }

    fun getStoresByNumber(storeNumber: String): Flow<List<StoreRecord>> =
        storeDao.getStoresByNumber(storeNumber)
            .map { entities -> entities.map {
                Log.i("getStoresByNumber repo","")

                StoreEntity.toStoreRecord(it)
            } }

    // Movement operations
    val allMovements: Flow<List<MovementRecord>> = movementDao.getAllMovements()
        .map { entities -> entities.map { MovementEntity.toMovementRecord(it) } }

//    fun getMovementsForDevice(deviceName: String,deviceSerialNumber: String): Flow<StoreRecord> =
//        movementDao.getMovementsForDevice(deviceName,deviceSerialNumber)
//            .map { entities -> entities.map { it.toStoreRecordRecord() } }

    val allUsers: Flow<List<StoreUsers>> = userDao.getAllUsers()
        .map { entities -> entities.map { it.toStoreUser() } }

fun searchDevicesByName(searchString: String): Flow<List<StoreEntity>>{
   return storeDao.searchDevicesByName(searchString)

}
    suspend fun refreshStores() {
        try {
            val remoteStores = StoreApi.retrofitService.getAllData()
            storeDao.insertAll(remoteStores.map { StoreRecord.toStoreEntity(it) })
        } catch (e: Exception) {
            throw e
        }
    }

    suspend fun refreshMovements() {
        try {
            val remoteMovements = StoreApi.retrofitService.getMovements()
            movementDao.insertAll(remoteMovements.map { MovementRecord.toMovementEntity(it) })
        } catch (e: Exception) {
            throw e
        }
    }

    suspend fun refreshUsers() {
        try {
            val remoteUsers = StoreApi.retrofitService.getAllUsers()
            userDao.insertAll(remoteUsers.map { it.toUserEntity() })
        } catch (e: Exception) {
            throw e
        }
    }

    // Add new movement and update local database
    suspend fun addMovement(movement: MovementRecord) {
        try {
//            val remoteMovement = StoreApi.retrofitService.addMovement(movement)
            movementDao.insertMovement(MovementRecord.toMovementEntity(movement))
            Log.i("YouClickedBorrow4", MovementRecord.toMovementEntity(movement).toString())

        } catch (e: Exception) {
            Log.i("YouClickedBorrow5",MovementRecord.toMovementEntity(movement).toString())

            throw e
        }
    }

    suspend fun updateDeviceStatusAfterMovement(deviceName: String,deviceSerialNumber: String, deviceStatus: String) {
        try {

            storeDao.updateDeviceStatus(deviceName = deviceName, deviceSerialNumber = deviceSerialNumber, deviceStatus = deviceStatus)
            Log.i("InsertItemID",deviceName +" "+ deviceSerialNumber)

        } catch (e: Exception) {
            throw e
        }
    }

    suspend fun updateMovementReturnDateAndTime(deviceName: String,deviceSerialNumber: String, returnDate: String,returnTime: String) {
        try {
            movementDao.updateMovementReturnDateAndTime(deviceName = deviceName, deviceSerialNumber = deviceSerialNumber,returnDate = returnDate , returnTime = returnTime )
            Log.i("InsertItemID",deviceName +" "+ deviceSerialNumber)

        } catch (e: Exception) {
            throw e
        }
    }


    // The following function used for edit a device information
    suspend fun editDeviceInformation(storeRecord: StoreRecord){
        storeDao.editDeviceInformation(StoreRecord.toStoreEntity(storeRecord))
    }

    // The following function used for add  a new device information

    private suspend fun addNewDeviceInformationInternal(storeRecord: StoreRecord){
        val deviceWithId = storeRecord.copy(
            id = "${storeRecord.deviceName}_${storeRecord.deviceSerialNumber}_${System.currentTimeMillis()}"
        )
        storeDao.addNewDeviceInformationInternal(StoreRecord.toStoreEntity(deviceWithId))
        Log.i("New Device2",deviceWithId.toString())
    }

suspend fun getDeviceCount(deviceName: String, deviceSerialNumber: String): Int{
    return storeDao.getDeviceCount(deviceName = deviceName,deviceSerialNumber = deviceSerialNumber)
}


    /**
     * Adds a new device after checking for duplicates.
     * @return true if device was added successfully, false if a duplicate was found
     */
    suspend fun addNewDeviceInformation(addedDevice: StoreRecord): Boolean {
        // Here we get first the number of the devices with that information
        val count = getDeviceCount(addedDevice.deviceName, addedDevice.deviceSerialNumber)
        return if (count > 0) {
            false // Device already exists
        } else {
            addNewDeviceInformationInternal(addedDevice)
            true // Device not exists
        }
    }



    // The following function used to fetch data from the server and insert it into the room database
    suspend fun fetchDataFromServerAndInsertItIntoDatabase(){

            val api = StoreApi.retrofitService
            val stores = api.getAllData()
            Log.i("NowIGetTheData1",stores.toString())
            storeDao.insertAll(stores.map { StoreRecord.toStoreEntity(it) })
            val movements = api.getMovements()
        Log.i("NowIGetTheData1",movements.toString())

        movementDao.insertAll(movements.map { MovementRecord.toMovementEntity(it) })

    }
}
