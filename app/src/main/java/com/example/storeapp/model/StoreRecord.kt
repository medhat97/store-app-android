package com.example.storeapp.model

import java.util.Date


data class StoreRecord(
    val id: String = "",
    val deviceName:String = "",
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
)

