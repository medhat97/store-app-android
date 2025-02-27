package com.example.storeapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.storeapp.model.StoreRecord
import java.util.Date


@Entity(tableName = "devices")
data class StoreEntity(
    @PrimaryKey
    val id: String = "",
    val deviceName: String = "",
    val deviceSerialNumber: String = "",
    val addingDate: Date = Date(),
    val deviceOfficialName: String = "",
    val deviceOfficialSerial: String = "",
    val deviceProject: String = "",
    val deviceNotes: String = "",
    val deviceImageStatus: String = "",
    val deviceStatus: String = "",
    val rackNumber: String = "",
    val shelveNumber: String = "",
    val storeNumber: String = ""
){
    companion object
}


// Used to convert StoreEntity to StoreRecord

fun StoreEntity.Companion.toStoreRecord(storeEntity: StoreEntity): StoreRecord {
    return StoreRecord(
        id = storeEntity.id,
        deviceName = storeEntity.deviceName,
        deviceSerialNumber = storeEntity.deviceSerialNumber,
        addingDate = storeEntity.addingDate,
        deviceOfficialName = storeEntity.deviceOfficialName,
        deviceOfficialSerial = storeEntity.deviceOfficialSerial,
        deviceProject = storeEntity.deviceProject,
        deviceNotes = storeEntity.deviceNotes,
        deviceImageStatus = storeEntity.deviceImageStatus,
        deviceStatus = storeEntity.deviceStatus,
        rackNumber = storeEntity.rackNumber,
        shelveNumber = storeEntity.shelveNumber,
        storeNumber = storeEntity.storeNumber
    )
}

// Used to convert StoreRecord to Store Entity
fun StoreRecord.Companion.toStoreEntity(storeRecord: StoreRecord): StoreEntity {
    return StoreEntity(
        id = storeRecord.id,
        deviceName = storeRecord.deviceName,
        deviceSerialNumber = storeRecord.deviceSerialNumber,
        addingDate = storeRecord.addingDate,
        deviceOfficialName = storeRecord.deviceOfficialName,
        deviceOfficialSerial = storeRecord.deviceOfficialSerial,
        deviceProject = storeRecord.deviceProject,
        deviceNotes = storeRecord.deviceNotes,
        deviceImageStatus = storeRecord.deviceImageStatus,
        deviceStatus = storeRecord.deviceStatus,
        rackNumber = storeRecord.rackNumber,
        shelveNumber = storeRecord.shelveNumber,
        storeNumber = storeRecord.storeNumber
    )
}
