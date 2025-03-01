package com.example.storeapp.data

import android.net.Uri

data class CameraState(
    val hasPermission: Boolean = false,
    val imageUri: Uri? = null,
    val currentDeviceName: String = ""
)
