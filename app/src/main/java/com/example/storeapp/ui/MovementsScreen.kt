//package com.example.storeapp.ui
//
//import android.util.Log
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.WindowInsets
//import androidx.compose.foundation.layout.systemBars
//import androidx.compose.foundation.layout.windowInsetsPadding
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.foundation.lazy.items
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.unit.dp
//
//@Composable
//fun InoutScreen(
//    storeUiState: StoreUiState,
//    viewModel: StoreViewModel){
//
//
//    LazyColumn (
//        modifier = Modifier.windowInsetsPadding(WindowInsets.systemBars),
//        verticalArrangement = Arrangement.spacedBy(8.dp)
//    ){
//        items(storeUiState.movementsData
//        ) {
//                storeRecord -> ItemCard(storeRecord = storeRecord, modifier = Modifier)
//            Log.i("Data",storeRecord.toString())
//        }
//    }
//}