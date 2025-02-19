package com.example.storeapp.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.storeapp.data.TabType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import androidx.lifecycle.viewModelScope
import com.example.storeapp.data.LoadingStatus
import com.example.storeapp.model.MovementRecord
import com.example.storeapp.model.StoreRecord
import com.example.storeapp.model.UpdateDeviceStatus
import com.example.storeapp.network.StoreApi
import kotlinx.coroutines.launch
import java.io.IOException

class StoreViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(StoreUiState())
    val uiState: StateFlow<StoreUiState> = _uiState

    init {
        getStoresList()
        getMovementList()
        getAllData()
    }


    fun getAllData(){
        viewModelScope.launch {


            try {
                val listResult = StoreApi.retrofitService.getAllData()

                _uiState.update {
                    it.copy(
                        storeDataForMovement = listResult
                    )
                }
            } catch (e: IOException){
                Log.i("Error Provider",e.toString())

                _uiState.update {
                    it.copy(
                    )
                }
            }

        }
    }


    fun updateCurrentTab(tabType: TabType) {
        _uiState.update {
            it.copy(
                currentTab = tabType
            )
        }
    }

    fun getStoreData(currentStoreId: String){
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    currentLoadingStatus = LoadingStatus.LOADING
                )
            }

            try {
                val listResult = StoreApi.retrofitService.getData(storeNumber = currentStoreId)

                _uiState.update {
                    it.copy(
                        data = listResult,
                        currentNameSearchList = listResult,
                        currentLoadingStatus = LoadingStatus.SUCCESS
                    )
                }
            } catch (e: IOException){
                Log.i("Error Provider",e.toString())

                _uiState.update {
                    it.copy(
                        currentLoadingStatus = LoadingStatus.FAILED
                    )
                }
            }

        }
    }

    fun changeExpandStatus(isBoxExpanded: Boolean){
        _uiState.update {
            it.copy(
                isBoxExpanded = !isBoxExpanded
            )
        }
    }
    fun changeBorrowExpandStatus(isBoxExpanded: Boolean){
        _uiState.update {
            it.copy(
                currentRecipientDropExpand = !isBoxExpanded
            )
        }
    }
    fun updateDropBoxTextField(textFieldContent: String){
        _uiState.update {
            it.copy(
                currentSelectedStore = textFieldContent
            )
        }
    }

    fun updateBorrowTextField(textFieldContent: String){
        _uiState.update {
            it.copy(
                currentDialogRecipient = textFieldContent
            )
        }
    }

    fun updateSearchTextField(textFieldContent: String){
        _uiState.update {
            it.copy(
                currentSearchName = textFieldContent
            )
        }
    }


    fun dismissDropBox(){
        _uiState.update {
            it.copy(
                isBoxExpanded = false
            )
        }
    }


    fun getMovementList(){
        viewModelScope.launch {


            try {
                val movementsList = StoreApi.retrofitService.getMovements()
                Log.i("All movementsList",movementsList.toString())

                _uiState.update {
                    it.copy(
                        movementsData = movementsList,
                        currentLoadingStatus = LoadingStatus.SUCCESS
                    )
                }
            } catch (e: Exception) {
                Log.i("Error Provideraaaaa",e.toString())

                _uiState.update {
                    it.copy(
                        currentLoadingStatus = LoadingStatus.FAILED
                    )
                }
            }
        }
    }

    fun getUsersList(){
        viewModelScope.launch {


            try {
                val usersList = StoreApi.retrofitService.getAllUsers()

                _uiState.update {
                    it.copy(
                        usersList = usersList
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        currentLoadingStatus = LoadingStatus.FAILED
                    )
                }
            }
        }
    }



    fun getStoresList(){
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    currentLoadingStatus = LoadingStatus.LOADING
                )
            }

            try {
                val storesList = StoreApi.retrofitService.getStoreData()

                _uiState.update {
                    it.copy(
                        stores = storesList,
                        currentLoadingStatus = LoadingStatus.SUCCESS
                    )
                }
            } catch (e: Exception) {

                _uiState.update {
                    it.copy(
                        currentLoadingStatus = LoadingStatus.FAILED
                    )
                }
            }
        }
    }

    fun searchData(currentSearchName: String,totalList: List<StoreRecord>){
        val searchResult = totalList.filter {
            it.deviceName.contains(currentSearchName,ignoreCase = true)
        }
        _uiState.update {
            it.copy(
                currentNameSearchList = searchResult
            )
        }
    }

    fun clearSearchTextField(){
        _uiState.update {
            it.copy(
                currentSearchName = ""
            )
        }
    }

    fun scanDataSearch(currentSearchName: String,totalList: List<StoreRecord>){

        val searchQRRecord = (totalList.filter {
            it.deviceName.contains(currentSearchName,ignoreCase = true) })[0]

        _uiState.update {
            it.copy(
                currentQRScanRecord = searchQRRecord
            )
        }
    }
    fun updateCardExpand(cardExpandStatus: Boolean){
        _uiState.update {
            it.copy(
                storeCardExpand = !cardExpandStatus
            )
        }

    }

    fun updateDialogShow(DialogShowStatus  : Boolean){
        _uiState.update {
            it.copy(
                isBorrowDropShown = DialogShowStatus
            )
        }

    }
    fun updateCurrentStoreRecord(storeRecord: StoreRecord){
        _uiState.update {
            it.copy(
                currentChosenStoreRecord = storeRecord
            )
        }
    }
    fun addMovement(movementRecord: MovementRecord){
        viewModelScope.launch {
            try {
                StoreApi.retrofitService.addMovement(movementRecord)
            }
            catch (e: IOException){
                Log.i("Error Provider move",e.toString())}
        }
    }


    fun updateDeviceStatusAfterMovement(storeRecord: StoreRecord,deviceStatus: String) {
        viewModelScope.launch {
            try {


                val result = StoreApi.retrofitService.updateDeviceStatus(
                    id = storeRecord.id,
                    updateRequest = UpdateDeviceStatus(deviceStatus)
                )

                getStoreData(_uiState.value.currentSelectedStore)
                getMovementList()
                getAllData()
                Log.i("Update Status", result.toString())

                _uiState.update {
                    it.copy(currentChosenStoreRecord = result)
                }
            } catch (e: Exception) {
                Log.e("Update Status", "Failed to update status", e)
            }
        }
    }


    fun changeConfirmDialogStatus(isExpanded: Boolean){
        _uiState.update {
            it.copy(
                currentConfirmDialogStatus = !isExpanded
            )
        }
    }


}
