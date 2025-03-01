package com.example.storeapp.ui

import com.example.storeapp.data.LoadingStatus
import com.example.storeapp.data.SearchType
import com.example.storeapp.data.TabType
import com.example.storeapp.model.StoreRecord
import com.example.storeapp.model.MovementRecord
import com.example.storeapp.model.StoreUsers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

data class StoreUiState(
    val currentTab: TabType = TabType.HOME,
    val data: Flow<List<StoreRecord>> = flowOf(emptyList()),
    val currentLoadingStatus: LoadingStatus = LoadingStatus.LOADING,
    val stores: List<String> = listOf("ST01","ST02","ST03","ST04"),
    val isBoxExpanded: Boolean = false,
    val currentSelectedStore: String = "",
    val currentSearchName: String = "",
    val currentNameSearchList: List<StoreRecord> = listOf(),
    val currentQRScanRecord: Flow<List<StoreRecord>> = flowOf(emptyList()),
    val movementsData: List<MovementRecord> = listOf(),
    val storeDataForMovement: List<StoreRecord> = listOf(),
    val storeCardExpand: Boolean = false,
    val currentDialogLoaner: String = "",
    val currentDialogRecipient: String = "",
    val currentLoanDropExpand: Boolean = false,
    val currentRecipientDropExpand: Boolean = false,
    val isBorrowDropShown: Boolean = false,
    val usersList: List<String> = listOf("Ahmed Elsaadany","Medhat Qurtam"),
    val currentChosenStoreRecord: StoreRecord = StoreRecord(),
    val currentConfirmDialogStatus: Boolean = false,
    val serverConnection:Boolean = false,
    val lastSyncTime: String = "Not synced yet",
    val isMovementBoxExpanded: Boolean = false,
    val currentSelectedSearchType: String = "",
    val searchTypes: List<SearchType> = listOf(SearchType.Recipient,SearchType.Status,SearchType.Store),
    val editDialogExpand: Boolean = false,
    val addDialogExpand: Boolean = false,
    val editedStoreValues: Map<String, String> = emptyMap(),
    val addedStoreValues: StoreRecord = StoreRecord(),
    val deviceExistTextExpand: Boolean = false,
    val userMessage:String = ""
)