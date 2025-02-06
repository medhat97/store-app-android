package com.example.storeapp.ui

import android.app.Activity
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.google.zxing.integration.android.IntentIntegrator

@Composable
fun ScanScreen() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        QRScannerButton { scannedData ->
            Toast.makeText(LocalContext.current, "Scanned: $scannedData", Toast.LENGTH_LONG).show()
        }
    }
}

@Composable
fun QRScannerButton(onScanResult: @Composable (String) -> Unit) {
    val context = LocalContext.current
    val activity = context as? Activity

    Button(onClick = {
        activity?.let {
            val intent = IntentIntegrator(it)
            intent.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE)
            intent.setPrompt("Scan a QR Code")
            intent.setBeepEnabled(true)
            intent.initiateScan()
        }
    }) {
        Text("Scan QR Code")
    }
}