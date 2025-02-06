package com.example.storeapp.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun StoreApp(){
    val viewModel: StoreViewModel = viewModel()
    val storeUiState = viewModel.uiState.collectAsState().value



    StoreHomeScreen(
        viewModel = viewModel,
        storeUiState = storeUiState
    )


}


@Preview
@Composable
fun StorePreview(){
    val viewModel: StoreViewModel = viewModel()
    val storeUiState = viewModel.uiState.collectAsState().value
    StoreHomeScreen(
        viewModel = viewModel,
        storeUiState = storeUiState)}