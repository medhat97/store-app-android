package com.example.storeapp.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.storeapp.R
import com.example.storeapp.data.LoadingStatus

@Composable
fun HomeScreen(
    storeUiState: StoreUiState
){
    when(storeUiState.currentLoadingStatus){
        LoadingStatus.SUCCESS -> SuccessScreen(storeUiState = storeUiState)
        LoadingStatus.LOADING -> LoadingScreen()
        LoadingStatus.FAILED -> ErrorScreen()
    }
}

@Composable
fun SuccessScreen(storeUiState: StoreUiState){
    Text(
        text = storeUiState.data
    )
}

@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
    Image(
        modifier = modifier.size(600.dp),

        painter = painterResource(R.drawable.loading_img),
        contentDescription = ""
    )}
}

@Composable
fun ErrorScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = modifier.size(200.dp),
            painter = painterResource(id = R.drawable.ic_connection_error), contentDescription = ""
        )
        Text(text = stringResource(R.string.loading_failed), modifier = Modifier.padding(16.dp))
    }
}