package com.example.storeapp.ui


import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.storeapp.model.MovementRecord
import com.example.storeapp.model.StoreUsers
import java.time.LocalDate
import java.time.LocalTime

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun BorrowDialog(
    viewModel: StoreViewModel,
    storeUiState: StoreUiState,

    ){

    Dialog(onDismissRequest = {
        viewModel.updateDialogShow(false)
        viewModel.updateBorrowTextField("")
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

                BorrowDropBox(
                    value = storeUiState.currentDialogRecipient,
                    expand = storeUiState.currentRecipientDropExpand,
                    onExpandChange = {viewModel.changeBorrowExpandStatus(storeUiState.currentRecipientDropExpand)},

                    namesList = storeUiState.usersList,
                    onDismiss = {},
                    label = "Recipient",
                    viewModel = viewModel

                )


                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp, alignment = Alignment.CenterHorizontally)
                ) {

                    Button(onClick = {
                        viewModel.addMovement(
                            MovementRecord(
                            deviceName = storeUiState.currentChosenStoreRecord.deviceName,
                            deviceSerialNumber = storeUiState.currentChosenStoreRecord.deviceSerialNumber,
                            deviceProject = storeUiState.currentChosenStoreRecord.deviceProject,
                            deviceNotes = storeUiState.currentChosenStoreRecord.deviceNotes,
                            receiverAdmin = storeUiState.currentDialogRecipient,
                            deviceOfficialName = storeUiState.currentChosenStoreRecord.deviceOfficialName,
                            storeNumber = storeUiState.currentChosenStoreRecord.storeNumber,
                            loanDate = LocalDate.now().toString(),
                            loanTime = LocalTime.now().toString()
                        )
                        )
                        Log.i("YouClickedBorrow1",storeUiState.toString())
                        viewModel.updateDeviceStatusAfterMovement(
                            storeRecord = storeUiState.currentChosenStoreRecord,
                            deviceStatus = "OUT"
                        )

                        viewModel.updateDialogShow(false)
                        viewModel.getStoreData(storeUiState.currentSelectedStore)
                        viewModel.getMovementList()


                    }, shape = RoundedCornerShape(6.dp) ,modifier = Modifier.width(100.dp)) { Text("Ok") }



                    Button(onClick = {
                        viewModel.updateDialogShow(false)
                    },shape = RoundedCornerShape(6.dp) ,modifier = Modifier.width(100.dp)) { Text("Cancel") }

                }

            }


        }


    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BorrowDropBox(

    value:String,
    expand:Boolean,
    onExpandChange: (Boolean) -> Unit,
    namesList:List<String>,
    onDismiss: () -> Unit,
    label: String,
    viewModel: StoreViewModel){
    Column (modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 8.dp))
    {
        ExposedDropdownMenuBox(
            expanded = expand,
            onExpandedChange = onExpandChange

        ) {

            TextField(
                value = value,
                onValueChange = {},
                readOnly = true,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expand)}
                , modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth(),
                label = { Text(text = label) }

            )

            ExposedDropdownMenu(
                expanded = expand,
                onDismissRequest = onDismiss
            ) {

                namesList.forEach {
                        item ->

                    DropdownMenuItem(

                        text = { Text(text = item) },
                        onClick = {
                            viewModel.updateBorrowTextField(item)
                            viewModel.changeBorrowExpandStatus(true)

                        }
                    )
                }




            }
        }
    }
}