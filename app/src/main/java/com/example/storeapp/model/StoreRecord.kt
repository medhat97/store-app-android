package com.example.storeapp.model

import java.util.Date


data class StoreRecord(
    val id: String,
    val device_name:String,
    val device_serial_number: String,
    val adding_date: Date,
    val device_official_name: String,
    val device_official_serial: String,
    val device_project: String,
    val device_notes: String,
    val device_image_status: String,
    val device_status: String,
    val rack_number: String,
    val shelve_number: String,
    val store_id: String
)

