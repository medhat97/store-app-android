package com.example.storeapp.ui

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.storeapp.model.StoreRecord

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun EditDialog(
    viewModel: StoreViewModel,
    storeRecord: StoreRecord,
    storeUiState: StoreUiState) {
    // Initialize the edit fields when the dialog is shown
    LaunchedEffect(storeRecord) {
        viewModel.initializeEditDialogFields(storeRecord)
    }

    Dialog(onDismissRequest = {
        viewModel.changeEditDialogExpand(false)
        viewModel.clearEditDialogFields()

    }) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 4.dp, vertical = 8.dp), // Reduced horizontal padding to take more width
            elevation = CardDefaults.cardElevation(8.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = "Edit device",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    OutlinedTextField(
                        value = storeUiState.editedStoreValues["deviceName"] ?: storeRecord.deviceName,
                        onValueChange = {
//                        viewModel.updateEditDialogField("deviceName", it)
                                    },
                        readOnly = true,
                        enabled = false,
                        label = { Text("Device Name") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(6.dp)
                    )

                    OutlinedTextField(
                        value = storeUiState.editedStoreValues["deviceSerialNumber"] ?: storeRecord.deviceSerialNumber,
                        onValueChange = {
                            viewModel.updateEditDialogField("deviceSerialNumber", it)
                                    },
                        readOnly = true,
                        enabled = false,
                        label = { Text("Device Serial Number") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(6.dp)
                    )

                    OutlinedTextField(
                        value = storeUiState.editedStoreValues["deviceOfficialName"] ?: storeRecord.deviceOfficialName,
                        onValueChange = { viewModel.updateEditDialogField("deviceOfficialName", it) },
                        label = { Text("Device Official Name") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(6.dp)
                    )

                    OutlinedTextField(
                        value = storeUiState.editedStoreValues["deviceOfficialSerial"] ?: storeRecord.deviceOfficialSerial,
                        onValueChange = { viewModel.updateEditDialogField("deviceOfficialSerial", it) },
                        label = { Text("Device Official Serial") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(6.dp)
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        OutlinedTextField(
                            value = storeUiState.editedStoreValues["shelveNumber"]
                                ?: storeRecord.shelveNumber,
                            onValueChange = { viewModel.updateEditDialogField("shelveNumber", it) },
                            label = { Text("Shelf") },
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(6.dp)
                        )
                        OutlinedTextField(
                            value = storeUiState.editedStoreValues["rackNumber"]
                                ?: storeRecord.rackNumber,
                            onValueChange = { viewModel.updateEditDialogField("rackNumber", it) },
                            label = { Text("Rack") },
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(6.dp)
                        )
                        OutlinedTextField(
                            value = storeUiState.editedStoreValues["storeNumber"]
                                ?: storeRecord.storeNumber,
                            onValueChange = { viewModel.updateEditDialogField("storeNumber", it) },
                            label = { Text("Store") },
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(6.dp)
                        )
                    }

                    OutlinedTextField(
                        value = storeUiState.editedStoreValues["deviceProject"] ?: storeRecord.deviceProject,
                        onValueChange = { viewModel.updateEditDialogField("deviceProject", it) },
                        label = { Text("Project") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(6.dp)
                    )

                    OutlinedTextField(
                        value = storeUiState.editedStoreValues["deviceNotes"] ?: storeRecord.deviceNotes,
                        onValueChange = { viewModel.updateEditDialogField("deviceNotes", it) },
                        label = { Text("Notes") },
                        modifier = Modifier.fillMaxWidth(),
                        minLines = 3,
                        shape = RoundedCornerShape(6.dp)
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.End)
                ) {
                    TextButton(
                        onClick = {

                            viewModel.clearEditDialogFields()
                            viewModel.changeEditDialogExpand(false)
                        },
                        shape = RoundedCornerShape(6.dp)
                    ) {
                        Text("Cancel", fontSize = 16.sp)
                    }
                    
                    Button(
                        onClick = {
                            viewModel.editDeviceInformation(storeRecord)
                            viewModel.clearEditDialogFields()
                            viewModel.changeEditDialogExpand(false)
                            viewModel.getStoreData(storeUiState.currentSelectedStore)

                        },
                        shape = RoundedCornerShape(6.dp)
                    ) {
                        Text("Save", fontSize = 16.sp)
                    }
                }
            }
        }
    }
}
