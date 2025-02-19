package com.example.storeapp.ui


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

@Composable
fun ConfirmDialog(
    viewModel: StoreViewModel,
    storeUiState: StoreUiState,
){

    Dialog(onDismissRequest = {
        viewModel.changeConfirmDialogStatus(storeUiState.currentConfirmDialogStatus)

    }) {

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
                Text(text = "Are you sure ?")
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp, alignment = Alignment.CenterHorizontally)
                ) {
                    Button(onClick = {
                        viewModel.updateDeviceStatusAfterMovement(
                            storeRecord = storeUiState.currentChosenStoreRecord,
                            deviceStatus = "IN"
                        )
                        viewModel.changeConfirmDialogStatus(true)

                    }) { Text(text = "Yes") }

                    Button(onClick = {
                        viewModel.changeConfirmDialogStatus(true)
                    }) { Text(text = "No") }



                }

            }
        }
    }

}