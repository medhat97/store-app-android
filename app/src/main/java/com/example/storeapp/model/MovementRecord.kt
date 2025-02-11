package com.example.storeapp.model

import java.sql.Time
import java.util.Date

data class MovementRecord(
    val device_name: String = "",
    val device_serial_number: String = "",
    val loan_date: Date = Date(),
    val loan_time: Time,
    val return_date:Date = Date(),
    val return_time:Time,
    val loaner_admin: String = "",
    val receiver_admin: String = "",
    val client: String = "",
    val device_project: String = "",
    val device_official_name: String = "",
    val super_visor_admin: String = "",
    val device_notes: String = "",
    val store_number: String = "",

)
