package com.example.storeapp.ui

import com.example.storeapp.data.LoadingStatus
import com.example.storeapp.data.TabType
import com.example.storeapp.model.StoreRecord
import com.example.storeapp.model.StoresList
import com.example.storeapp.model.MovementRecord
import com.example.storeapp.model.StoreUsers

data class StoreUiState(
    val currentTab: TabType = TabType.HOME,
    val data: List<StoreRecord> = listOf(),
    val currentLoadingStatus: LoadingStatus = LoadingStatus.LOADING,
    val stores: List<StoresList> =listOf(),
    val isBoxExpanded: Boolean = false,
    val currentSelectedStore: String = "",
    val currentSearchName: String = "",
    val currentNameSearchList:List<StoreRecord> = listOf(),
    val currentQRScanRecord: StoreRecord = StoreRecord(),
    val movementsData: List<MovementRecord> = listOf(),
    val storeDataForMovement:List<StoreRecord> = listOf(),
    val storeCardExpand: Boolean = false,
    val currentDialogLoaner: String = "",
    val currentDialogRecipient: String = "",
    val currentLoanDropExpand: Boolean = false,
    val currentRecipientDropExpand: Boolean = false,
    val isBorrowDropShown: Boolean = false,
    val usersList: List<StoreUsers> = listOf(),
    val currentChosenStoreRecord: StoreRecord = StoreRecord(),
    val currentConfirmDialogStatus: Boolean = false

)