package com.example.storeapp.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.storeapp.data.TabType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import androidx.lifecycle.viewModelScope
import com.example.storeapp.data.LoadingStatus
import com.example.storeapp.model.StoreRecord
import com.example.storeapp.network.StoreApi
import kotlinx.coroutines.launch
import java.io.IOException

class StoreViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(StoreUiState())
    val uiState: StateFlow<StoreUiState> = _uiState

    init {
        getStoresList()
    }


    fun getAllData(){
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    currentLoadingStatus = LoadingStatus.LOADING
                )
            }

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
                        currentLoadingStatus = LoadingStatus.FAILED
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
                val listResult = StoreApi.retrofitService.getData(store_id = currentStoreId)

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

    fun updateDropBoxTextField(textFieldContent: String){
        _uiState.update {
            it.copy(
                currentSelectedStore = textFieldContent
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
            _uiState.update {
                it.copy(
                    currentLoadingStatus = LoadingStatus.LOADING
                )
            }

            try {
                val movementsList = StoreApi.retrofitService.getMovements()

                _uiState.update {
                    it.copy(
                        movementsData = movementsList,
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

}
