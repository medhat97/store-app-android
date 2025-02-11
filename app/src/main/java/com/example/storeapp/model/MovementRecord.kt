package com.example.storeapp.model

import java.sql.Time
import java.util.Date

data class MovementRecord(
    val deviceName: String = "",
    val deviceSerialNumber: String = "",
    val loanDate: Date = Date(),
    val loanTime: Time,
    val returnDate:Date = Date(),
    val returnTime:Time,
    val loanerAdmin: String = "",
    val receiverAdmin: String = "",
    val client: String = "",
    val deviceProject: String = "",
    val deviceOfficialName: String = "",
    val superVisorAdmin: String = "",
    val deviceNotes: String = "",
    val storeNumber: String = "",

)
