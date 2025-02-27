package com.example.storeapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.storeapp.model.MovementRecord

@Entity(tableName = "movement")
data class MovementEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val deviceName: String = "",
    val deviceSerialNumber: String = "",
    val loanDate: String = "",
    val loanTime: String = "",
    val returnDate:String = "",
    val returnTime:String = "",
    val loanerAdmin: String = "",
    val receiverAdmin: String = "",
    val client: String = "",
    val deviceProject: String = "",
    val deviceOfficialName: String = "",
    val superVisorAdmin: String = "",
    val deviceNotes: String = "",
    val storeNumber: String = "",
){
    companion object
}

fun MovementEntity.Companion.toMovementRecord(movementEntity: MovementEntity): MovementRecord {
    return MovementRecord(
        id = movementEntity.id,
        deviceName = movementEntity.deviceName,
        deviceSerialNumber = movementEntity.deviceSerialNumber,
        loanDate = movementEntity.loanDate,
        loanTime = movementEntity.loanTime,
        loanerAdmin = movementEntity.loanerAdmin,
        returnDate = movementEntity.returnDate,
        returnTime = movementEntity.returnTime,
        receiverAdmin = movementEntity.receiverAdmin,
        client = movementEntity.client,
        deviceProject = movementEntity.deviceProject,
        deviceOfficialName = movementEntity.deviceOfficialName,
        superVisorAdmin = movementEntity.superVisorAdmin,
        deviceNotes = movementEntity.deviceNotes,
        storeNumber = movementEntity.storeNumber
    )
}

fun MovementRecord.Companion.toMovementEntity(movementRecord: MovementRecord): MovementEntity {
    return MovementEntity(
        id = movementRecord.id,
        deviceName = movementRecord.deviceName,
        deviceSerialNumber = movementRecord.deviceSerialNumber,
        loanDate = movementRecord.loanDate,
        loanTime = movementRecord.loanTime,
        loanerAdmin = movementRecord.loanerAdmin,
        returnDate = movementRecord.returnDate,
        returnTime = movementRecord.returnTime,
        receiverAdmin = movementRecord.receiverAdmin,
        client = movementRecord.client,
        deviceProject = movementRecord.deviceProject,
        deviceOfficialName = movementRecord.deviceOfficialName,
        superVisorAdmin = movementRecord.superVisorAdmin,
        deviceNotes = movementRecord.deviceNotes,
        storeNumber = movementRecord.storeNumber
    )
}
