package com.example.storeapp.ui

import android.app.Activity
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.storeapp.model.StoreRecord
import com.google.zxing.integration.android.IntentIntegrator

@Composable
fun ScanScreen(
    viewModel: StoreViewModel,
    storeUiState: StoreUiState
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Display the ItemCard with the current scanned record
        ItemCard(
            storeRecord = storeUiState.currentQRScanRecord,
            modifier = Modifier.padding(16.dp)
        )

        QRScannerButton { scannedData ->
            // When QR is scanned, search for the record and update UI state
            viewModel.scanDataSearch(scannedData, storeUiState.data)
            // Optionally show a toast to indicate successful scan
            Toast.makeText(LocalContext.current, "Scanned: $scannedData", Toast.LENGTH_SHORT).show()
        }
    }
}

@Composable
fun QRScannerButton(onScanResult: @Composable (String) -> Unit) {
    val context = LocalContext.current
    val activity = context as? Activity

    Button(
        onClick = {
            activity?.let {
                val intent = IntentIntegrator(it)
                intent.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE)
                intent.setPrompt("Scan a QR Code")
                intent.setBeepEnabled(true)
                intent.initiateScan()
            }
        },
        modifier = Modifier.padding(top = 16.dp)
    ) {
        Text("Scan QR Code")
    }
}