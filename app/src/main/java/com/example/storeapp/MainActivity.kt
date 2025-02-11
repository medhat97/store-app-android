package com.example.storeapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.storeapp.ui.StoreApp
import com.example.storeapp.ui.StoreViewModel
import com.example.storeapp.ui.theme.StoreAppTheme
import com.google.zxing.integration.android.IntentIntegrator

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val viewModel: StoreViewModel = viewModel()
            val storeUiState = viewModel.uiState.collectAsState().value
            StoreAppTheme {

                    StoreApp(viewModel = viewModel, storeUiState = storeUiState)

            }
        }
    }




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



