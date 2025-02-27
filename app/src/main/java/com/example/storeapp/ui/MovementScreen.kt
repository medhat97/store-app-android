package com.example.storeapp.ui

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.storeapp.model.StoreRecord
import kotlinx.coroutines.flow.first

@Composable
fun MovementsScreen(
    viewModel: StoreViewModel,
    storeUiState: StoreUiState) {

    Column(
        modifier = Modifier
            .windowInsetsPadding(WindowInsets.systemBars)
            .padding(top = 0.dp, bottom = 16.dp, start = 16.dp, end = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        Card(
            modifier = Modifier
                .fillMaxWidth(),
            elevation = CardDefaults.cardElevation(4.dp),
            shape = RoundedCornerShape(12.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                MovementDropBox(
                    viewModel = viewModel,
                    storeUiState = storeUiState,
                    modifier = Modifier.fillMaxWidth()
                )

                TextField(
                    value = storeUiState.currentSearchName,
                    onValueChange = {
                        viewModel.updateSearchTextField(it)
                        viewModel.searchData(
                            currentSearchName = it)
                    },
                    label = { Text(text = "Type here") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp)
                )
            }
        }



        LazyColumn(
            modifier = Modifier.windowInsetsPadding(WindowInsets.systemBars),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(storeUiState.movementsData) { movementRecord ->
                val matchingStore = storeUiState.storeDataForMovement.find {
                    it.deviceSerialNumber == movementRecord.deviceSerialNumber &&
                            it.deviceName == movementRecord.deviceName
                } ?: StoreRecord()

                MovementCard(
                    movementRecord = movementRecord,
                    storeRecord = matchingStore
                )
            }
        }
    }
}