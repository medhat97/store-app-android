package com.example.storeapp.ui

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.storeapp.R
import com.example.storeapp.data.LoadingStatus
import com.example.storeapp.model.StoreRecord

@Composable
fun HomeScreen(
    storeUiState: StoreUiState,
    viewModel: StoreViewModel
){


    when(storeUiState.currentLoadingStatus){
        LoadingStatus.SUCCESS -> SuccessScreen(storeUiState = storeUiState, viewModel = viewModel)
        LoadingStatus.LOADING -> LoadingScreen()
        LoadingStatus.FAILED -> ErrorScreen()
    }
}

@Composable
fun SuccessScreen(storeUiState: StoreUiState,viewModel: StoreViewModel){
    Column(
        modifier = Modifier
            .windowInsetsPadding(WindowInsets.systemBars),
        horizontalAlignment = Alignment.CenterHorizontally) {



            StoreDropBox(
                viewModel = viewModel,
                storeUiState = storeUiState,
                modifier = Modifier
            )




        TextField(
            value= storeUiState.currentSearchName,
            onValueChange = {viewModel.updateSearchTextField(it)},
            label = { Text(text = "Search by name") }
        )



        Button(
            onClick = {
                viewModel.searchData(
                    currentSearchName = storeUiState.currentSearchName,
                    totalList = storeUiState.data)

            }
        ) {
            Text(text = "Search")
        }




        LazyColumn (modifier = Modifier.windowInsetsPadding(WindowInsets.systemBars)){
            items(storeUiState.data) {
                    storeRecord -> ItemCard(storeRecord = storeRecord, modifier = Modifier)
                Log.i("Data",storeRecord.toString())
            }
        }
    }



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


@Composable
fun ItemCard(storeRecord: StoreRecord, modifier: Modifier = Modifier){
    Card(modifier = modifier.padding(10.dp)) {
        Column(modifier = Modifier.fillMaxWidth().padding(10.dp)) {
            Text(text = storeRecord.id)
            Text(text = storeRecord.device_name)
            Text(text = storeRecord.store_id)

        }

    }

}