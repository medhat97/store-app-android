package com.example.storeapp.model


import java.sql.Time
import java.util.Date

data class MovementRecord(
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

    )
