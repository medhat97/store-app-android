package com.example.storeapp.ui

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun StoreApp(
    viewModel: StoreViewModel = viewModel()

) {
    val uiState by viewModel.uiState.collectAsState()
    
//    uiState.userMessage?.let { message ->
//        LaunchedEffect(message) {
//            Toast.makeText(LocalContext.current, message, Toast.LENGTH_LONG).show()
//            viewModel.clearUserMessage()
//        }
//    }

    StoreHomeScreen(
        viewModel = viewModel,
        storeUiState = uiState
    )
}
