package com.example.storeapp.ui


import android.os.Build
import android.util.Log
import android.widget.Toast
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.storeapp.model.StoreRecord

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AddDialog(
    viewModel: StoreViewModel,
    storeUiState: StoreUiState) {

    val context = LocalContext.current

    Dialog(onDismissRequest = {
        viewModel.changeAddDialogExpand(false)
        viewModel.clearAddDialogFields()


    }) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 4.dp, vertical = 8.dp),
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
                    text = "Add device",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                if(storeUiState.deviceExistTextExpand){
                    Text(
                        text = storeUiState.userMessage,
                        fontSize = 14.sp,
                        color = Color.Red,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                }

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    OutlinedTextField(
                        value = storeUiState.addedStoreValues.deviceName,
                        onValueChange = {
                            viewModel.updateAddDialogField("deviceName",it)
                        },
                        label = { Text("Device Name") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(6.dp)
                    )

                    OutlinedTextField(
                        value = storeUiState.addedStoreValues.deviceSerialNumber,
                        onValueChange = {
                            viewModel.updateAddDialogField("deviceSerialNumber", it)
                        },

                        label = { Text("Device Serial Number") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(6.dp)
                    )

                    OutlinedTextField(
                        value = storeUiState.addedStoreValues.deviceOfficialName,
                        onValueChange = { viewModel.updateAddDialogField("deviceOfficialName", it) },
                        label = { Text("Device Official Name") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(6.dp)
                    )

                    OutlinedTextField(
                        value = storeUiState.addedStoreValues.deviceOfficialSerial,
                        onValueChange = { viewModel.updateAddDialogField("deviceOfficialSerial", it) },
                        label = { Text("Device Official Serial") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(6.dp)
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        OutlinedTextField(
                            value = storeUiState.addedStoreValues.shelveNumber,
                            onValueChange = { viewModel.updateAddDialogField("shelveNumber", it) },
                            label = { Text("Shelf") },
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(6.dp)
                        )
                        OutlinedTextField(
                            value = storeUiState.addedStoreValues.rackNumber,
                            onValueChange = { viewModel.updateAddDialogField("rackNumber", it) },
                            label = { Text("Rack") },
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(6.dp)
                        )
                        OutlinedTextField(
                            value = storeUiState.addedStoreValues.storeNumber,
                            onValueChange = { viewModel.updateAddDialogField("storeNumber", it) },
                            label = { Text("Store") },
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(6.dp)
                        )
                    }

                    OutlinedTextField(
                        value = storeUiState.addedStoreValues.deviceProject,
                        onValueChange = { viewModel.updateAddDialogField("deviceProject", it) },
                        label = { Text("Project") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(6.dp)
                    )

                    OutlinedTextField(
                        value = storeUiState.addedStoreValues.deviceNotes,
                        onValueChange = { viewModel.updateAddDialogField("deviceNotes", it) },
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

                            viewModel.changeAddDialogExpand(false)
                            viewModel.clearAddDialogFields()
                            viewModel.changeErrorMessageExpand(false)



                        },
                        shape = RoundedCornerShape(6.dp)
                    ) {
                        Text("Cancel", fontSize = 16.sp)
                    }

                    Button(
                        onClick = {
                            if (storeUiState.addedStoreValues.deviceName.isEmpty() || 
                                storeUiState.addedStoreValues.deviceSerialNumber.isEmpty()) {
                                viewModel.changeErrorMessageExpand(true)
                                viewModel.updateUserMessage("Please fill in device name and serial number")
                            } else {
                                viewModel.changeErrorMessageExpand(false)
                                viewModel.clearUserMessage()
                                viewModel.addNewDevice(storeUiState.addedStoreValues)
                            }
                        },
                        shape = RoundedCornerShape(6.dp)
                    ) {
                        Text("Add", fontSize = 16.sp)
                    }
                }
            }
        }
    }
}
