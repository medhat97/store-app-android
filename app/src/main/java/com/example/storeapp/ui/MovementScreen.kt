package com.example.storeapp.ui

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.storeapp.model.StoreRecord

@Composable
fun MovementsScreen(
    storeUiState: StoreUiState,
    viewModel: StoreViewModel){

    Column(
        modifier = Modifier
            .windowInsetsPadding(WindowInsets.systemBars)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        LazyColumn (
            modifier = Modifier.windowInsetsPadding(WindowInsets.systemBars),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ){

            Log.i("Error Providerbbbbb",storeUiState.movementsData.toString())

            items(storeUiState.movementsData) {

                    movementRecord ->
//            val storeDataMatchRecord = (storeUiState.storeDataForMovement.filter {
//                it.deviceSerialNumber.contains(movementRecord.deviceSerialNumber) })[0]
                val storeDataMatchRecord = storeUiState.storeDataForMovement.first{
                    it.deviceSerialNumber.contains(movementRecord.deviceSerialNumber)
                }

                MovementCard(movementRecord = movementRecord, storeRecord = storeDataMatchRecord)
                Log.i("Error Providerbdddd","error here")

            }
        }}
}