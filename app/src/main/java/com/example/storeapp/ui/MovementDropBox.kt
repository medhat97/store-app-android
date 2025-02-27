package com.example.storeapp.ui

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField

import androidx.compose.runtime.Composable

import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.map

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovementDropBox(
    storeUiState: StoreUiState,
    viewModel: StoreViewModel,
    modifier: Modifier = Modifier){

    Column (modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 8.dp)){
        ExposedDropdownMenuBox(
            expanded = storeUiState.isMovementBoxExpanded,
            onExpandedChange = {viewModel.changeMovementExpandStatus(storeUiState.isMovementBoxExpanded)}

        ) {

            TextField(
                value = storeUiState.currentSelectedSearchType,
                onValueChange = {},
                readOnly = true,
                trailingIcon = {ExposedDropdownMenuDefaults.TrailingIcon(expanded = storeUiState.isMovementBoxExpanded)}
                , modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth(),
                label = { Text(text = "Search by") }

            )

            ExposedDropdownMenu(
                expanded = storeUiState.isMovementBoxExpanded,
                onDismissRequest = {viewModel.dismissMovementDropBox()}
            ) {
                storeUiState.searchTypes.map {
                        item ->

                    DropdownMenuItem(

                        text = { Text(text = item.name) },
                        onClick = {
//                            viewModel.updateDropBoxTextField(item.name)
//                            viewModel.dismissDropBox()
//                            viewModel.getStoreData(item)
//                            Log.i("DropStore",item)
//                            viewModel.clearSearchTextField()

                        }
                    )
                }

            }




        }
    }
}

