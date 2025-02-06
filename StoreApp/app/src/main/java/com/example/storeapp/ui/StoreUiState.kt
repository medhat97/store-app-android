package com.example.storeapp.ui

import com.example.storeapp.data.LoadingStatus
import com.example.storeapp.data.TabType

data class StoreUiState(
    val currentTab: TabType = TabType.HOME,
    val data: String = "",
    val currentLoadingStatus: LoadingStatus = LoadingStatus.LOADING
)