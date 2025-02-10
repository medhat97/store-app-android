package com.example.storeapp.ui

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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StoreDropBox(
    storeUiState: StoreUiState,
    viewModel: StoreViewModel,
    modifier: Modifier = Modifier){

Column (modifier = Modifier
    .fillMaxWidth()
    .padding(horizontal = 8.dp)){
    ExposedDropdownMenuBox(
        expanded = storeUiState.isBoxExpanded,
        onExpandedChange = {viewModel.changeExpandStatus(storeUiState.isBoxExpanded)}

    ) {

        TextField(
            value = storeUiState.currentSelectedStore,
            onValueChange = {},
            readOnly = true,
            trailingIcon = {ExposedDropdownMenuDefaults.TrailingIcon(expanded = storeUiState.isBoxExpanded)}
            , modifier = Modifier
                .menuAnchor()
                .fillMaxWidth(),
            label = { Text(text = "Choose a store") }

        )

        ExposedDropdownMenu(
            expanded = storeUiState.isBoxExpanded,
            onDismissRequest = {viewModel.dismissDropBox()}
        ) {
            storeUiState.stores.forEach {
                    item ->
                DropdownMenuItem(

                    text = { Text(text = item.id) },
                    onClick = {
                        viewModel.updateDropBoxTextField(item.id)
                        viewModel.dismissDropBox()
                        viewModel.getStoreData(item.id)
                        viewModel.clearSearchTextField()

                    }
                )
            }




        }
    }
}

}