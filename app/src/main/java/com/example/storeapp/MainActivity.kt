package com.example.storeapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.storeapp.ui.StoreApp
import com.example.storeapp.ui.theme.StoreAppTheme
import com.google.zxing.integration.android.IntentIntegrator

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            StoreAppTheme {

                    StoreApp()
//                scan()

            }
        }
    }


//    @Composable
//    fun scan() {
//        Column(
//            modifier = Modifier.fillMaxSize(),
//            horizontalAlignment = Alignment.CenterHorizontally,
//            verticalArrangement = Arrangement.Center
//        ) {
//            QRScannerButton { scannedData ->
//                Toast.makeText(LocalContext.current, "Scanned: $scannedData", Toast.LENGTH_LONG).show()
//            }
//        }
//    }
//
//    @Composable
//    fun QRScannerButton(onScanResult: @Composable (String) -> Unit) {
//        val context = LocalContext.current
//        val activity = context as? Activity
//
//        Button(onClick = {
//            activity?.let {
//                val intent = IntentIntegrator(it)
//                intent.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE)
//                intent.setPrompt("Scan a QR Code")
//                intent.setBeepEnabled(true)
//                intent.initiateScan()
//            }
//        }) {
//            Text("Scan QR Code")
//        }
//    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {
            if (result.contents != null) {
                Toast.makeText(this, "Scanned: ${result.contents}", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "Scan Canceled", Toast.LENGTH_LONG).show()
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }
}



