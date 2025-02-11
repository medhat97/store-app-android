package com.example.storeapp.ui

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.storeapp.R
import com.example.storeapp.data.LoadingStatus
import com.example.storeapp.model.StoreRecord
import com.example.storeapp.ui.theme.StoreAppTheme

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
            .windowInsetsPadding(WindowInsets.systemBars)
            .padding(16.dp),
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
                StoreDropBox(
                    viewModel = viewModel,
                    storeUiState = storeUiState,
                    modifier = Modifier.fillMaxWidth()
                )

                TextField(
                    value = storeUiState.currentSearchName,
                    onValueChange = {
                        viewModel.updateSearchTextField(it)
                        viewModel.searchData(
                            currentSearchName = it,
                            totalList = storeUiState.data
                        )
                    },
                    label = { Text(text = "Search by name") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp)
                )
            }
        }

        LazyColumn (
            modifier = Modifier.windowInsetsPadding(WindowInsets.systemBars),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ){
            items(storeUiState.currentNameSearchList
            ) {
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
