package com.example.storeapp.ui

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun StoreApp(){
    val viewModel: StoreViewModel = viewModel()
    val storeUiState = viewModel.uiState.collectAsState().value

    StoreHomeScreen(
        viewModel = viewModel,
        storeUiState = storeUiState
    )


}


